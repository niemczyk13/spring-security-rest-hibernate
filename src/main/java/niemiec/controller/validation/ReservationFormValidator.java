package niemiec.controller.validation;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.ComparisonOfReservationsHours;
import niemiec.model.RestaurantTable;
import niemiec.restaurant.RestaurantInformations;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationFormValidator {
	private final int SECONDS_IN_MINUTE = 60;
	
	private RestaurantTableService restaurantTableService;
	private ComparisonOfReservationsHours comparison;

	public ReservationFormValidator() {
	}

	@Autowired
	public ReservationFormValidator(RestaurantTableService restaurantTableService, ComparisonOfReservationsHours comparison) {
		this.restaurantTableService = restaurantTableService;
		this.comparison = comparison;
	}

	public void validate(ReservationForm form, Errors errors) {
		// TODO DODAĆ OPCJĘ CZY CZAS WEJŚCIA NIE JEST PÓŹNIEJSZY NIŻ WYJŚCIA
		validateRestaurantTable(form, errors);
		checkIfTheGivenHoursAreInWorkingHours(form, errors);
		checkIfReservationTheRightTimeBeforeClosing(form, errors);
		checkIfReservationLastsAMinimumTime(form, errors);
	}

	private void validateRestaurantTable(ReservationForm form, Errors errors) {
		RestaurantTable restaurantTable;
		restaurantTable = getRestaurantTableFromDataBase(form.getTableNumber());
		if (restaurantTableExist(restaurantTable)) {
			checkIfTheTableHasEnoughSeats(restaurantTable, form, errors);
		} else {
			String message = "Table does not exist";
			errors.rejectValue("tableNumber", message, message);
		}
	}

	private void checkIfTheTableHasEnoughSeats(RestaurantTable restaurantTable, ReservationForm form, Errors errors) {
		if (restaurantTable.getNumberOfSeats() < form.getNumberOfPeople()) {
			String message = "The maximum number of places for table number " + restaurantTable.getTableNumber()
					+ " is " + restaurantTable.getNumberOfSeats();
			errors.rejectValue("numberOfPeople", message, message);
		}
	}

	private RestaurantTable getRestaurantTableFromDataBase(int tableNumber) {
		return restaurantTableService.getByTableNumber(tableNumber);
	}

	private boolean restaurantTableExist(RestaurantTable restaurantTable) {
		return restaurantTable != null;
	}

	private void checkIfReservationLastsAMinimumTime(ReservationForm form, Errors errors) {
		LocalTime startHour = form.getStartHour();
		LocalTime endHour = form.getEndHour();
		long minimumReservationTime = RestaurantInformations.MINIMUM_RESERVATION_TIME;

		if (!comparison.checkIfReservationLastsAMinimumTime(startHour, endHour,
				minimumReservationTime)) {
			minimumReservationTime = minimumReservationTime / SECONDS_IN_MINUTE;
			String message = "The minimum rezervation time is " + minimumReservationTime;
			errors.rejectValue("startHour", message, message);
		}

	}

	private void checkIfReservationTheRightTimeBeforeClosing(ReservationForm form, Errors errors) {
		LocalTime startHour = form.getStartHour();
		LocalTime closeHour = RestaurantInformations.CLOSE_HOUR;
		long timeBeforeClosing = RestaurantInformations.MINIMUM_RESERVATION_TIME_BEFORE_CLOSING;

		if (!comparison.checkIfReservationTheRightTimeBeforeClosing(startHour, closeHour,
				timeBeforeClosing)) {
			timeBeforeClosing = timeBeforeClosing / SECONDS_IN_MINUTE;
			String message = "The reservation minimum " + timeBeforeClosing + " minutes before closing restauration";
			errors.rejectValue("startHour", message, message);
		}
	}

	private void checkIfTheGivenHoursAreInWorkingHours(ReservationForm form, Errors errors) {
		LocalTime startHour = form.getStartHour();
		LocalTime endHour = form.getEndHour();
		LocalTime openHour = RestaurantInformations.OPEN_HOUR;
		LocalTime closeHour = RestaurantInformations.CLOSE_HOUR;

		if (!comparison.checkIfTheGivenHoursAreInWorkingHours(startHour, endHour, openHour,
				closeHour)) {
			String message = "The restaurant is open from " + openHour + " to " + closeHour;
			errors.rejectValue("startHour", message, message);
		}
	}

}
