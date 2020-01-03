package niemiec.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import niemiec.model.Reservation;
import niemiec.repository.ReservationRepository;

public class ReservationDAOImpl implements ReservationDAO {
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public List<Reservation> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long save(Reservation reservation) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(long id, Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

}
