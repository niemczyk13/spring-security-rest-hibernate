package niemiec.controller.validation;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.ComparisonOfReservationsHours;
import niemiec.model.RestaurantTable;
import niemiec.restaurant.RestaurantInformation;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationValidator implements Validator {
	private RestaurantTableService restaurantTableService;
	
	
	@Autowired
	public ReservationValidator(RestaurantTableService restaurantTableService) {
		this.restaurantTableService = restaurantTableService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ReservationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		// sama walidacja, sprawdzanie czy dany termin wolny, czy stolik istnieje itp.
		// rezerwcaja w osobnej klasie w kontrolerze

		ReservationForm form = (ReservationForm) target;
		// czy dany stolik istnieje
		RestaurantTable restaurantTable;
		restaurantTable = getRestaurantTableFromDataBase(form.getTableNumber());
		if (checkIfRestaurantTableExist(restaurantTable, errors)) {
			// czy ilość miejsc w stoliku jest większa lub równa liście gości
			
			// czy podany termin jest wolny
			// jeżeli nie jest to informacja jak pod formularzem i propozycje innego stolika
			// lub innych godzin
		}
		
		// czy podane godziny w godzinach pracy
		checkIfTheGivenHoursAreInWorkingHours(form, errors);
		
		//czy rezerwacja to minimum 30 minut
		
		
	}

	

	private void checkIfTheGivenHoursAreInWorkingHours(ReservationForm form, Errors errors) {
		LocalTime startHour = form.getStartHour();
		LocalTime endHour = form.getEndHour();
		LocalTime openHour = RestaurantInformation.OPEN_HOUR;
		LocalTime closeHour = RestaurantInformation.CLOSE_HOUR;
		if (ComparisonOfReservationsHours.checkIfTheGivenHoursAreInWorkingHours(startHour, endHour, openHour, closeHour)) {
			// TODO
		} else {
			String message = "The restaurant is open from " + openHour + " to " + closeHour;
			errors.rejectValue("startHour", message, message);
		}
		
	}

	private RestaurantTable getRestaurantTableFromDataBase(int tableNumber) {
			return restaurantTableService.getByTableNumber(tableNumber);
	}
	

	private boolean checkIfRestaurantTableExist(RestaurantTable restaurantTable, Errors errors) {
		if (restaurantTable == null) {
			String message = "Table does not exist";
			errors.rejectValue("tableNumber", message, message);
			return false;
		}
		return true;
	}

}
