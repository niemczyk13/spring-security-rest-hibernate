package niemiec.controller.validation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.ComparisonOfReservationsHours;
import niemiec.model.RestaurantTable;
import niemiec.service.restaurantTable.RestaurantTableService;

public class ReservationFormValidatorTest {
	ReservationFormValidator validator;
	ReservationForm reservationForm;
	RestaurantTableService service;
	RestaurantTable table;
	Errors errors;
	ComparisonOfReservationsHours comparison;

	DateTimeFormatter dateFormtter;
	DateTimeFormatter timeFormatter;
	@Before
	public void init() {
		dateFormtter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		table = createTable();
		
		service = mock(RestaurantTableService.class);
		errors = mock(Errors.class);
		
		reservationForm = createReservationForm();
		
		comparison = new ComparisonOfReservationsHours();
		validator = new ReservationFormValidator(service, comparison);
	}

	private RestaurantTable createTable() {
		RestaurantTable table = new RestaurantTable();
		table.setId(1);
		table.setNumberOfSeats(2);
		table.setTableNumber(22);
		return table;
	}

	private ReservationForm createReservationForm() {
		ReservationForm reservationForm = new ReservationForm();
		reservationForm.setDate(createLocalDate("2020-05-02"));
		reservationForm.setEmail("niemczyk13@o2.pl");
		reservationForm.setName("Arek");
		reservationForm.setSurname("Niemiec");
		reservationForm.setNumberOfPeople(2);
		reservationForm.setPhoneNumber("7732832932");
		reservationForm.setTableNumber(1);
		reservationForm.setStartHour(createLocalTime("19:00"));
		reservationForm.setEndHour(createLocalTime("19:20"));
		return reservationForm;
	}

	@Test
	public void testValidate() {
		when(service.getByTableNumber(22)).thenReturn(table);
		validator.validate(reservationForm, errors);
		// TODO
	}

	private LocalTime createLocalTime(String hour) {
		return LocalTime.parse(hour, timeFormatter);
	}
	
	private LocalDate createLocalDate(String date) {
		return LocalDate.parse(date, dateFormtter);
	}
}
