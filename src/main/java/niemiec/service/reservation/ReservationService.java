package niemiec.service.reservation;

import java.util.List;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;

public interface ReservationService {
	long save(Reservation reservation);
	void update(long id, Reservation reservation);
	void delete(long id);
	Reservation get(long id);
	List<Reservation> list();
	boolean checkIfThereIsAFreeDate(ReservationForm reservationForm);
}
