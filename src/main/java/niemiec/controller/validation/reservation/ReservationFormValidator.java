package niemiec.controller.validation.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.comparisionHours.ComparisonOfReservationsHours;
import niemiec.model.RestaurantTable;
import niemiec.restaurant.OpeningHoursInfo;
import niemiec.restaurant.ReservationTimeDetailsInfo;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationFormValidator {
	private final int SECONDS_IN_MINUTE = 60;
	
	private RestaurantTableService restaurantTableService;
	private ComparisonOfReservationsHours comparison;
	
	private LocalDate date;
	private LocalTime startHour;
	private LocalTime endHour;
	private final LocalTime openHour = OpeningHoursInfo.OPEN.hour();
	private final LocalTime closeHour = OpeningHoursInfo.CLOSE.hour();
	private int tableNumber;
	private int numberOfPeople;
	private Errors errors;

	public ReservationFormValidator() {
	}

	@Autowired
	public ReservationFormValidator(RestaurantTableService restaurantTableService, ComparisonOfReservationsHours comparison) {
		this.restaurantTableService = restaurantTableService;
		this.comparison = comparison;
	}

	public void validate(ReservationForm form, Errors errors) {
		createStartupVariables(form, errors);
		validateRestaurantTable();
		if (hoursAndDateNotNull()) {
			checkIfTheTimeOfEntryIsBeforeTheTimeOfDeparture();
			checkIfTheGivenHoursAreInWorkingHours();
			checkIfReservationTheRightTimeBeforeClosing();
			checkIfReservationLastsAMinimumTime();
		}
	}

	private boolean hoursAndDateNotNull() {
		if (!Optional.ofNullable(date).isPresent()) {
			return false;
		} else if (!Optional.ofNullable(startHour).isPresent()) {
			return false;
		} else if (!Optional.ofNullable(endHour).isPresent()) {
			return false;
		}
		return true;
	}

	private void createStartupVariables(ReservationForm form, Errors errors) {
		this.date = form.getDate();
		this.startHour = form.getStartHour();
		this.endHour = form.getEndHour();
		this.tableNumber = form.getTableNumber();
		this.numberOfPeople = form.getNumberOfPeople();
		this.errors = errors;
	}

	private void checkIfTheTimeOfEntryIsBeforeTheTimeOfDeparture() {
		if (!comparison.checkIfTheTimeOfEntryIsBeforeTheTimeOfDeparture(date, startHour, endHour)) {
			String message = "Czas wejścia nie może być pózniej niż czas wyjścia";
			String errorCode = "reservation.starHour";
			errors.rejectValue("startHour", errorCode, message);
		}
	}

	private void validateRestaurantTable() {
		RestaurantTable restaurantTable;
		restaurantTable = getRestaurantTableFromDataBase(tableNumber);
		if (restaurantTableExist(restaurantTable)) {
			checkIfTheTableHasEnoughSeats(restaurantTable);
		} else {
			String message = "Stolik o podanym numerze nie istnieje";
			String errorCode = "reservation.table";
			errors.rejectValue("tableNumber", errorCode, message);
		}
	}

	private void checkIfTheTableHasEnoughSeats(RestaurantTable restaurantTable) {
		if (restaurantTable.getNumberOfSeats() < numberOfPeople) {
			String message = "Maksymalna ilość ludzi w stoliku nr " + restaurantTable.getTableNumber()
					+ " wynosi " + restaurantTable.getNumberOfSeats();
			String errorCode = "reservation.numberOfPeople";
			errors.rejectValue("numberOfPeople", errorCode, message);
		}
	}

	private RestaurantTable getRestaurantTableFromDataBase(int tableNumber) {
		return restaurantTableService.getByTableNumber(tableNumber);
	}

	private boolean restaurantTableExist(RestaurantTable restaurantTable) {
		return Optional.ofNullable(restaurantTable).isPresent();
	}

	private void checkIfReservationLastsAMinimumTime() {
		long minimumReservationTime = ReservationTimeDetailsInfo.MINIMUM_RESERVATION_TIME.time();

		if (!comparison.checkIfReservationLastsAMinimumTime(date, startHour, endHour,
				minimumReservationTime)) {
			minimumReservationTime = minimumReservationTime / SECONDS_IN_MINUTE;
			String message = "Minimalny czas rezerwacji wynosi " + minimumReservationTime;
			String errorCode = "reservation.startHour";
			errors.rejectValue("startHour", errorCode, message);
		}

	}

	private void checkIfReservationTheRightTimeBeforeClosing() {
		long timeBeforeClosing = ReservationTimeDetailsInfo.MINIMUM_RESERVATION_TIME_BEFORE_CLOSING.time();

		if (!comparison.checkIfReservationTheRightTimeBeforeClosing(date, startHour, closeHour,
				timeBeforeClosing)) {
			timeBeforeClosing = timeBeforeClosing / SECONDS_IN_MINUTE;
			String message = "Rezerwacja może zostać dokonana minimum " + timeBeforeClosing + " minut przed zamknięciem restauracji";
			String errorCode = "reservation.startHour";
			errors.rejectValue("startHour", errorCode, message);
		}
	}

	private void checkIfTheGivenHoursAreInWorkingHours() {
		if (!comparison.checkIfTheGivenHoursAreInWorkingHours(date, startHour, endHour)) {
			String message = "Rezerwacja jest otwarta od " + openHour + " do " + closeHour;
			String errorCode = "reservation.startHour";
			errors.rejectValue("startHour", errorCode, message);
		}
	}

}
