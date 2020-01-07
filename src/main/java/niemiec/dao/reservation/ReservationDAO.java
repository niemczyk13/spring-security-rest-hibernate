package niemiec.dao.reservation;

import java.util.List;

import niemiec.dao.GenericDAO;
import niemiec.model.Reservation;

public interface ReservationDAO extends GenericDAO<Reservation> {

	List<Reservation> list();

	Reservation get(long id);

	long save(Reservation reservation);

	void update(long id, Reservation reservation);

	void delete(long id);

}
