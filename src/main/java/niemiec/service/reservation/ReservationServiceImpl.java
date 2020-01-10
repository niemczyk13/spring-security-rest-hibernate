package niemiec.service.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import niemiec.dao.GenericDAO;
import niemiec.form.ReservationForm;
import niemiec.model.Reservation;
import niemiec.model.Table;
import niemiec.service.table.TableService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private GenericDAO<Reservation> reservationDAO;
	private TableService tableService;
	
	public ReservationServiceImpl() {
	}

	@Autowired
	public ReservationServiceImpl(GenericDAO<Reservation> reservationDAO, TableService tableService) {
		this.reservationDAO = reservationDAO;
		this.tableService = tableService;
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

	@Override
	public boolean checkIfThereIsAFreeDate(ReservationForm reservationForm) {
		// TODO Auto-generated method stub
		
		//pobierz dany stolik
		Table table = tableService.get(reservationForm.getTableNumber());
		//dla danego stolika pobierz wszystkie rezerwacje po ID
		List<Reservation> reservations = table.getReservationsFromDate(reservationForm.getDate());
		//porównaj każdą rezerwację z datą i godzinami rozpoczęcia i zakończenia (godziny są pełne i wpół do)
		if (checkReservationsIsAFreeDate(reservations, reservationForm)) {
			
		}
		//jeżeli tak to utwórz rezerwację i dodaj do bazy danych
		return false;
	}

	private boolean checkReservationsIsAFreeDate(List<Reservation> reservations, ReservationForm reservationForm) {
		// TODO Auto-generated method stub
		LocalTime startHour = reservationForm.getStartHour();
		LocalTime endHour = reservationForm.getEndHour();
		
		
		return false;
	}
}
