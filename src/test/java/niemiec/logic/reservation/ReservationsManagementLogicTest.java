package niemiec.logic.reservation;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.comparisionHours.ComparisonOfReservationsHours;
import niemiec.response.ResponseToReservationRequest;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

public class ReservationsManagementLogicTest {
	DateTimeFormatter dateFormtter;
	DateTimeFormatter timeFormatter;
	ReservationsManagementLogic logic;
	
	@Mock
	private RestaurantTableService tableService;
	@Mock
	private ReservationService reservationService;
	private ComparisonOfReservationsHours comparison;
	

	
	@Before
	public void init() {
		dateFormtter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		comparison = new ComparisonOfReservationsHours();
		
		logic = new ReservationsManagementLogic(tableService, reservationService, comparison);
		// TODO
	}

	@Test
	public void testStartReservation() {
		
		ReservationForm form = createReservationForm();
		ResponseToReservationRequest response = logic.startReservation(form);
		
		ResponseToReservationRequest response2 = new ResponseToReservationRequest();
		
		// przynajmniej 2 rezerwacje
		// table bez rezerwacji
		// table z rezerwacją
		// 
		
		fail("Not yet implemented");
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
	
	private LocalTime createLocalTime(String hour) {
		return LocalTime.parse(hour, timeFormatter);
	}
	
	private LocalDate createLocalDate(String date) {
		return LocalDate.parse(date, dateFormtter);
	}

}
