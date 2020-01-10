package niemiec.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class TableTest {
	Table table;

	Reservation reservation1;
	Reservation reservation2;
	Reservation reservation3;
	List<Reservation> reservations;
	
	DateTimeFormatter dateFormtter;
	DateTimeFormatter timeFormatter;
	
	@Before
	public void init() {
		table = createTable(1, 2);
		
		reservation1 = createReservation(table, 1, 2, "2020-01-30", "19:00", "20:00");
		reservation2 = createReservation(table, 2, 2, "2020-01-30", "21:00", "22:00");
		reservation3 = createReservation(table, 3, 2, "2020-01-31", "21:00", "22:00");
		
		reservations = new ArrayList<Reservation>();
		reservations.add(reservation1);
		reservations.add(reservation2);
		reservations.add(reservation3);
		
		table.addReservation(reservation1);
		table.addReservation(reservation2);
		table.addReservation(reservation3);
	}

	@Test
	public void testGetReservationsFromDate() {
		int arraySize = 2;
		assertEquals(table.getReservations().get(2), reservations.get(2));
		
		LocalDate date = LocalDate.parse("2020-01-30", dateFormtter);
		assertEquals(table.getReservationsFromDate(date).size(), arraySize);
	}

	@Test
	public void testDeleteReservationsToDate() {
		assertEquals(table.getReservations().get(2), reservations.get(2));
		
		LocalDate date = LocalDate.parse("2020-01-31", dateFormtter);
		table.deleteReservationsToDate(date);
		assertEquals(table.getReservations().get(0), reservation3);
	}

	@Test
	public void testDeleteOneReservation() {
		assertEquals(table.getReservations().get(2), reservations.get(2));
		
		table.deleteOneReservation(reservation1.getId());
		assertEquals(table.getReservations().get(0), reservation2);
	}

	private Reservation createReservation(Table t, long id, int numberOfPeople, String date, String startTime,
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

	private Table createTable(int id, int numberOfSeats) {
		Table t = new Table();
		t.setId(id);
		t.setNumberOfSeats(numberOfSeats);
		
		return t;
	}
}
