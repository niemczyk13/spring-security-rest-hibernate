package niemiec.service.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import niemiec.dao.GenericDAO;
import niemiec.dao.table.TableDAOImpl;
import niemiec.model.Reservation;
import niemiec.model.Table;

public class TableServiceImplTest {
	GenericDAO<Table> mockTableDAO;
	TableServiceImpl tableService;
	Table table1;

	Reservation reservation1;
	Reservation reservation2;
	Reservation reservation3;
	
	DateTimeFormatter dateFormtter;
	DateTimeFormatter timeFormatter;

	@Before
	public void init() {
		table1 = createTable(1, 2);
		
		reservation1 = createReservation(table1, 1, 2, "2020-01-30", "19:00", "20:00");
		reservation2 = createReservation(table1, 2, 2, "2020-01-30", "21:00", "22:00");
		reservation3 = createReservation(table1, 3, 2, "2020-01-31", "21:00", "22:00");
		
		table1.addReservation(reservation1);
		table1.addReservation(reservation2);
		table1.addReservation(reservation3);
		
		mockTableDAO = mock(TableDAOImpl.class);
		tableService = new TableServiceImpl(mockTableDAO);
	}

	@Test
	public void testGetAllReservationsAndFromDate() {
		int id = 1;
		when(mockTableDAO.get(id)).thenReturn(table1);
		
		LocalDate date = LocalDate.parse("2020-01-30", dateFormtter);
		
		assertEquals(2, tableService.getReservationsFromDate(id, date).size());
		assertEquals(3, tableService.getAllReservations(id).size());
	}
	
	@Test
	public void updateWithDeleteReservationToDateTest() {
		int id = 1;
		Table table2 = createTable((int) table1.getId(), table1.getNumberOfSeats());
		table2.addReservation(reservation3);
		
		when(mockTableDAO.get(id)).thenReturn(table1);
		
		assertNotEquals(table1.getReservations().get(0), table2.getReservations().get(0));
		
		LocalDate date = LocalDate.parse("2020-01-31", dateFormtter);
		tableService.deleteReservationsToDate(id, table1, date);
		
		assertEquals(table1.getReservations().get(0), table2.getReservations().get(0));
		
	}
	
	@Test
	public void deleteOneReservation() {
		int id = 1;
		Table table2 = createTable((int) table1.getId(), table1.getNumberOfSeats());
		table2.addReservation(reservation1);
		table2.addReservation(reservation3);
		
		when(mockTableDAO.get(id)).thenReturn(table1);
		
		assertNotEquals(table1.getReservations().get(1), table2.getReservations().get(1));
		
		tableService.deleteOneReservation(id, 2);
		
		assertEquals(table1.getReservations().get(1), table2.getReservations().get(1));
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
		t.setNumber(id);
		t.setNumberOfSeats(numberOfSeats);
		
		return t;
	}
}
