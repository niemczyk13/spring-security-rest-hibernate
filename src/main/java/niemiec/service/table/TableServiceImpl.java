package niemiec.service.table;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import niemiec.dao.GenericDAO;
import niemiec.model.Reservation;
import niemiec.model.Table;

@Service
public class TableServiceImpl implements TableService {
	
	private GenericDAO<Table> tableDAO;
	
	public TableServiceImpl() {
	}

	@Autowired
	public TableServiceImpl(GenericDAO<Table> tableDAO) {
		this.tableDAO = tableDAO;
	}

	@Override
	public long save(Table table) {
		return tableDAO.save(table);
	}

	@Override
	public void update(long id, Table table) {
		tableDAO.update(id, table);
	}

	@Override
	public void delete(long id) {
		tableDAO.delete(id);
	}

	@Override
	public Table get(long id) {
		return tableDAO.get(id);
	}

	@Override
	public List<Table> list() {
		return tableDAO.list();
	}
	
	@Override
	public List<Reservation> getReservationsFromDate(long id, LocalDate date) {
		List<Reservation> reservations = tableDAO.get(id).getReservations();
		return reservations.stream()
				.filter(r -> r.getDate().equals(date))
				.collect(Collectors.toList());
	}
	
	@Override
	public Table deleteReservationsToDate(long id, Table table, LocalDate date) {
		List<Reservation> reservations = table.getReservations().stream()
				.filter(r -> r.getDate().compareTo(date) >= 0)
				.collect(Collectors.toList());
		table.setReservations(reservations);
		return table;
	}
	
	@Override
	public Table deleteOneReservation(long tableId, long reservationId) {
		Table table = tableDAO.get(tableId);
		List<Reservation> reservations = table.getReservations().stream()
				.filter(r -> r.getId() != reservationId)
				.collect(Collectors.toList());
		table.setReservations(reservations);
		return table;
	}
}
