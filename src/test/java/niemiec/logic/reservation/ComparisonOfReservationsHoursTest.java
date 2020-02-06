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
	ComparisonOfReservationsHours comparison;
	Reservation reservation1;
	Reservation reservation2;
	Reservation reservation3;
	Reservation reservation4;
	List<Reservation> reservations;
	
	DateTimeFormatter dateFormtter;
	DateTimeFormatter timeFormatter;
	
	ComparisonOfReservationsHours comparison;
	
	@Before
	public void init() {
		comparison = new ComparisonOfReservationsHours();
		dateFormtter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		reservation1 = createReservation(null, 1, 2, "2020-01-30", "14:00", "15:00");
		reservation2 = createReservation(null, 2, 2, "2020-01-30", "17:00", "18:30");
		reservation3 = createReservation(null, 3, 2, "2020-01-30", "18:30", "19:00");
		reservation4 = createReservation(null, 3, 2, "2020-01-30", "19:30", "21:00");
		
		reservations = new ArrayList<Reservation>();
		reservations.add(reservation1);
		reservations.add(reservation2);
		reservations.add(reservation3);
		reservations.add(reservation4);
		
		comparison = new ComparisonOfReservationsHours();
	}

	@Test
	public void testCheckIfItIsFreeTime() {
		ReservationForm reservationForm = createReservationFormWithStartAndEndHours("13:00", "15:00");
		assertEquals(comparison.checkIfItIsFreeTime(reservations, reservationForm), false);
		
		reservationForm = createReservationFormWithStartAndEndHours("15:00", "16:00");
		assertEquals(comparison.checkIfItIsFreeTime(reservations, reservationForm), true);
		
		reservationForm = createReservationFormWithStartAndEndHours("15:00", "17:00");
		assertEquals(comparison.checkIfItIsFreeTime(reservations, reservationForm), true);
		
		reservationForm = createReservationFormWithStartAndEndHours("19:15", "19:20");
		assertEquals(comparison.checkIfItIsFreeTime(reservations, reservationForm), true);
		
		reservationForm = createReservationFormWithStartAndEndHours("20:30", "22:00");
		assertEquals(comparison.checkIfItIsFreeTime(reservations, reservationForm), false);
		
	}
	
	@Test
	public void testCheckIfTheGivenHoursAreInWorkingHours() {
		LocalTime openHour = createLocalTime("14:00");
		LocalTime closeHour = createLocalTime("00:00");
		
		LocalTime startHour = createLocalTime("14:00");
		LocalTime endHour = createLocalTime("15:00");
		
		assertEquals(checkBetweenWorkingHours(startHour, endHour, openHour, closeHour), true);
		
		
		openHour = createLocalTime("14:00");
		closeHour = createLocalTime("00:00");
		startHour = createLocalTime("13:59");
		endHour = createLocalTime("15:00");
		assertEquals(checkBetweenWorkingHours(startHour, endHour, openHour, closeHour), false);
		
		openHour = createLocalTime("14:00");
		closeHour = createLocalTime("00:00");
		startHour = createLocalTime("14:00");
		endHour = createLocalTime("00:01");
		assertEquals(checkBetweenWorkingHours(startHour, endHour, openHour, closeHour), false);
		
		openHour = createLocalTime("14:00");
		closeHour = createLocalTime("01:00");
		startHour = createLocalTime("14:00");
		endHour = createLocalTime("15:00");
		assertEquals(checkBetweenWorkingHours(startHour, endHour, openHour, closeHour), true);
		
		openHour = createLocalTime("14:00");
		closeHour = createLocalTime("01:00");
		startHour = createLocalTime("14:00");
		endHour = createLocalTime("01:01");
		assertEquals(checkBetweenWorkingHours(startHour, endHour, openHour, closeHour), false);
	

		closeHour = createLocalTime("01:00");
		startHour = createLocalTime("23:00");
		
	}
	
	@Test
	public void testCheckIfReservationTheRightTimeBeforeClosing() {
		long min = 3600L;
		LocalTime closeHour;
		LocalTime startHour;
		
		startHour = createLocalTime("23:00");
		closeHour = createLocalTime("01:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), true);
		
		startHour = createLocalTime("00:00");
		closeHour = createLocalTime("01:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), true);
		
		startHour = createLocalTime("00:01");
		closeHour = createLocalTime("01:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), false);

		startHour = createLocalTime("23:59");
		closeHour = createLocalTime("01:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), true);

		startHour = createLocalTime("23:45");
		closeHour = createLocalTime("00:30");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), false);

		startHour = createLocalTime("23:30");
		closeHour = createLocalTime("00:30");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), true);
		
		startHour = createLocalTime("23:31");
		closeHour = createLocalTime("00:30");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), false);
		
		startHour = createLocalTime("23:30");
		closeHour = createLocalTime("00:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), false);
		
		startHour = createLocalTime("23:00");
		closeHour = createLocalTime("00:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), true);
		
		startHour = createLocalTime("23:01");
		closeHour = createLocalTime("00:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), false);
		
		startHour = createLocalTime("22:51");
		closeHour = createLocalTime("23:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), false);
		
		startHour = createLocalTime("19:51");
		closeHour = createLocalTime("23:00");
		assertEquals(checkBeforeClosing(startHour, closeHour, min), true);
	}
	
	@Test
	public void testCheckIfReservationLastsAMinimumTime() {
		long min = 1800L;
		LocalTime startHour;
		LocalTime endHour;
		
		startHour = createLocalTime("14:00");
		endHour = createLocalTime("14:29");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), false);
		
		startHour = createLocalTime("14:00");
		endHour = createLocalTime("14:30");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), true);
		
		startHour = createLocalTime("14:00");
		endHour = createLocalTime("15:00");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), true);
		
		startHour = createLocalTime("23:45");
		endHour = createLocalTime("00:10");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), false);
		
		startHour = createLocalTime("23:45");
		endHour = createLocalTime("00:15");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), true);
		
		startHour = createLocalTime("00:00");
		endHour = createLocalTime("00:10");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), false);
		
		startHour = createLocalTime("00:00");
		endHour = createLocalTime("00:30");
		assertEquals(checkResrvationMinimumTime(startHour, endHour, min), true);
		
	}
	
	private Object checkResrvationMinimumTime(LocalTime startHour, LocalTime endHour, long min) {
		return comparison.checkIfReservationLastsAMinimumTime(startHour, endHour, min);
	}

	private boolean checkBeforeClosing(LocalTime startHour, LocalTime closeHour,
			long minReservationTimeBeforeClosing) {
		return comparison.checkIfReservationTheRightTimeBeforeClosing(startHour, closeHour, minReservationTimeBeforeClosing);
	}
	
	private boolean checkBetweenWorkingHours(LocalTime startHour, LocalTime endHour,
			LocalTime openHour, LocalTime closeHour) {
		return comparison.checkIfTheGivenHoursAreInWorkingHours(startHour, endHour, openHour, closeHour);
	}
	
	private ReservationForm createReservationFormWithStartAndEndHours(String startHour, String endHour) {
		ReservationForm reservationForm = new ReservationForm();
		reservationForm.setStartHour(createLocalTime(startHour));
		reservationForm.setEndHour(createLocalTime(endHour));
		return reservationForm;
	}

	private Reservation createReservation(RestaurantTable t, long id, int numberOfPeople, String date, String startTime,
			String endTime) {
		
		Reservation r = new Reservation();
		
		r.setId(id);
		r.setRestaurantTable(t);
		r.setNumberOfPeople(numberOfPeople);
		r.setDate(createLocalDate(date));
		
		r.setStartHour(createLocalTime(startTime));
		r.setEndHour(createLocalTime(endTime));
		
		return r;
	}
	
	private LocalTime createLocalTime(String hour) {
		return LocalTime.parse(hour, timeFormatter);
	}
	
	private LocalDate createLocalDate(String date) {
		return LocalDate.parse(date, dateFormtter);
	}

}
