package niemiec.dao.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import niemiec.model.Reservation;
import niemiec.repository.ReservationRepository;

@Component
public class ReservationDAOImpl implements ReservationDAO {
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public List<Reservation> list() {
		return reservationRepository.findAll();
	}

	@Override
	public Reservation get(long id) {
		return reservationRepository.findById(id);
	}

	@Override
	public long save(Reservation reservation) {
		return reservationRepository.save(reservation).getId();
	}

	@Override
	public void update(long id, Reservation reservation) {
		reservation.setId(id);
		reservationRepository.save(reservation);
	}

	@Override
	public void delete(long id) {
		reservationRepository.deleteById(id);
	}

}
