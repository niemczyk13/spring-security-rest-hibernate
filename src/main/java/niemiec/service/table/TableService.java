package niemiec.service.table;

import java.time.LocalDate;
import java.util.List;

import niemiec.model.Reservation;
import niemiec.model.Table;

public interface TableService {
	long save(Table table);
	void update(long id, Table table);
	void delete(long id);
	Table get(long id);
	List<Table> list();
	List<Reservation> getReservationsFromDate(long id, LocalDate date);
	Table deleteReservationsToDate(long id, Table table, LocalDate date);
	Table deleteOneReservation(long tableId, long reservationId);
}
