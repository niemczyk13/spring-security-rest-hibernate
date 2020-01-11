package niemiec.service.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import niemiec.dao.GenericDAO;
import niemiec.model.Reservation;

@Service
public class ReservationServiceImpl implements ReservationService {

	private GenericDAO<Reservation> reservationDAO;
	
	public ReservationServiceImpl() {
	}

	@Autowired
	public ReservationServiceImpl(GenericDAO<Reservation> reservationDAO) {
		this.reservationDAO = reservationDAO;
	}

	@Override
	public long save(Reservation reservation) {
		return reservationDAO.save(reservation);
	}

	@Override
	public void update(long id, Reservation reservation) {
		reservationDAO.update(id, reservation);
	}

	@Override
	public void delete(long id) {
		reservationDAO.delete(id);
	}

	@Override
	public Reservation get(long id) {
		return reservationDAO.get(id);
	}

	@Override
	public List<Reservation> list() {
		return reservationDAO.list();
	}

}
