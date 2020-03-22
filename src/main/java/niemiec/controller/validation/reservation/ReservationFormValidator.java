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
import niemiec.restaurant.RestaurantInformations;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationFormValidator {
	private final int SECONDS_IN_MINUTE = 60;
	
	private RestaurantTableService restaurantTableService;
	private ComparisonOfReservationsHours comparison;
	
	private LocalDate date;
	private LocalTime startHour;
	private LocalTime endHour;
	private LocalTime openHour = RestaurantInformations.OPEN_HOUR;
	private LocalTime closeHour = RestaurantInformations.CLOSE_HOUR;
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
		checkIfTheTimeOfEntryIsBeforeTheTimeOfDeparture();
		checkIfTheGivenHoursAreInWorkingHours();
		checkIfReservationTheRightTimeBeforeClosing();
		checkIfReservationLastsAMinimumTime();
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
			String message = "The entry time cannot be later than the exit time.";
			errors.reject(message);
		}
	}

	private void validateRestaurantTable() {
		RestaurantTable restaurantTable;
		restaurantTable = getRestaurantTableFromDataBase(tableNumber);
		if (restaurantTableExist(restaurantTable)) {
			checkIfTheTableHasEnoughSeats(restaurantTable);
		} else {
			String message = "Table does not exist";
			errors.rejectValue("tableNumber", message, message);
		}
	}

	private void checkIfTheTableHasEnoughSeats(RestaurantTable restaurantTable) {
		if (restaurantTable.getNumberOfSeats() < numberOfPeople) {
			String message = "The maximum number of places for table number " + restaurantTable.getTableNumber()
					+ " is " + restaurantTable.getNumberOfSeats();
			errors.rejectValue("numberOfPeople", message, message);
		}
	}

	private RestaurantTable getRestaurantTableFromDataBase(int tableNumber) {
		return restaurantTableService.getByTableNumber(tableNumber);
	}

	private boolean restaurantTableExist(RestaurantTable restaurantTable) {
		return Optional.ofNullable(restaurantTable).isPresent();
	}

	private void checkIfReservationLastsAMinimumTime() {
		long minimumReservationTime = RestaurantInformations.MINIMUM_RESERVATION_TIME;

		if (!comparison.checkIfReservationLastsAMinimumTime(date, startHour, endHour,
				minimumReservationTime)) {
			minimumReservationTime = minimumReservationTime / SECONDS_IN_MINUTE;
			String message = "The minimum rezervation time is " + minimumReservationTime;
			errors.rejectValue("startHour", message, message);
		}

	}

	private void checkIfReservationTheRightTimeBeforeClosing() {
		long timeBeforeClosing = RestaurantInformations.MINIMUM_RESERVATION_TIME_BEFORE_CLOSING;

		if (!comparison.checkIfReservationTheRightTimeBeforeClosing(date, startHour, closeHour,
				timeBeforeClosing)) {
			timeBeforeClosing = timeBeforeClosing / SECONDS_IN_MINUTE;
			String message = "The reservation minimum " + timeBeforeClosing + " minutes before closing restauration";
			errors.rejectValue("startHour", message, message);
		}
	}

	private void checkIfTheGivenHoursAreInWorkingHours() {
		if (!comparison.checkIfTheGivenHoursAreInWorkingHours(date, startHour, endHour, openHour,
				closeHour)) {
			String message = "The restaurant is open from " + openHour + " to " + closeHour;
			errors.rejectValue("startHour", message, message);
		}
	}

}
