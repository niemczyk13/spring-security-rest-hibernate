package niemiec.logic.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;
import niemiec.model.Table;
import niemiec.response.ResponseToReservationRequest;
import niemiec.service.reservation.ReservationService;
import niemiec.service.table.TableService;

@Component
public class ReservationsManagementLogic {
	
	private TableService tableService;
	private ReservationService reservationService;
	private ComparisonOfReservationsHours comparisonOfReservationsHours;
	private ResponseToReservationRequest response;
	
	
	public ReservationsManagementLogic() {
	}

	@Autowired
	public ReservationsManagementLogic(TableService tableService, ReservationService reservationService) {
		this.tableService = tableService;
		this.reservationService = reservationService;
	}

	public ResponseToReservationRequest startReservation(ReservationForm reservationForm) {
		// TODO Auto-generated method stub
		// sprawdzenie czy na daną datę i godzinę w danym stoliku istnieje rezerwacja
		
		Table table = tableService.get(reservationForm.getTableNumber());
		List<Reservation> reservations = table.getReservationsFromDate(reservationForm.getDate());
		
		if (comparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm)) {
			//jeżeli tak to tworzy rejestrację i dodaje do bazy
			//dodać stolik do rezerwacji
		} else {
			
		}
		
		// jeżeli jest zajęte to szuka wolnego stolika z wystarczającą liczbą miejsc na daną godzinę
		
		
		// podaje też wolne godziny +- 2h na chcianym i innych stolikach - póki co opcjlonalne
		
		// po zarejestrowaniu wysłanie emaila z potwierdzeniem
		return response;
	}
	
	
}
