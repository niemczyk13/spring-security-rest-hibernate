package niemiec.logic.reservation;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;

public class ComparisonOfReservationsHoursTest {
	ComparisonOfReservationsHours comparisonOfReservationsHours;
	
	Reservation reservation1;
	Reservation reservation2;
	Reservation reservation3;
	Reservation reservation4;
	List<Reservation> reservations;
	
	DateTimeFormatter dateFormtter;
	DateTimeFormatter timeFormatter;
	
	@Before
	public void init() {
		comparisonOfReservationsHours = new ComparisonOfReservationsHours();
		
		reservation1 = createReservation(null, 1, 2, "2020-01-30", "14:00", "15:00");
		reservation2 = createReservation(null, 2, 2, "2020-01-30", "17:00", "18:30");
		reservation3 = createReservation(null, 3, 2, "2020-01-30", "18:30", "19:00");
		reservation4 = createReservation(null, 3, 2, "2020-01-30", "19:30", "21:00");
		
		reservations = new ArrayList<Reservation>();
		reservations.add(reservation1);
		reservations.add(reservation2);
		reservations.add(reservation3);
		reservations.add(reservation4);
	}

	@Test
	public void testCheckIfItIsFreeTime() {
		ReservationForm reservationForm = createReservationFormWithStartAndEndHours("13:00", "15:00");
		assertEquals(comparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm), false);
		
		reservationForm = createReservationFormWithStartAndEndHours("15:00", "16:00");
		assertEquals(comparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm), true);
		
		reservationForm = createReservationFormWithStartAndEndHours("15:00", "17:00");
		assertEquals(comparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm), true);
		
		reservationForm = createReservationFormWithStartAndEndHours("19:15", "19:20");
		assertEquals(comparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm), true);
		
		reservationForm = createReservationFormWithStartAndEndHours("20:30", "22:00");
		assertEquals(comparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm), false);
	}
	
	private ReservationForm createReservationFormWithStartAndEndHours(String startHour, String endHour) {
		ReservationForm reservationForm = new ReservationForm();
		timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		reservationForm.setStartHour(LocalTime.parse(startHour, timeFormatter));
		reservationForm.setEndHour(LocalTime.parse(endHour, timeFormatter));
		return reservationForm;
	}

	private Reservation createReservation(RestaurantTable t, long id, int numberOfPeople, String date, String startTime,
			String endTime) {
		
		Reservation r = new Reservation();
		dateFormtter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		r.setId(id);
		r.setTable(t);
		r.setNumberOfPeople(numberOfPeople);
		r.setDate(LocalDate.parse(date, dateFormtter));
		
		r.setStartHour(LocalTime.parse(startTime, timeFormatter));
		r.setEndHour(LocalTime.parse(endTime, timeFormatter));
		
		return r;
	}

}
