package niemiec.logic.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;
import niemiec.response.ResponseToReservationRequest;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationsManagementLogic {
	
	private RestaurantTableService tableService;
	private ReservationService reservationService;
	private ResponseToReservationRequest response;
	
	
	public ReservationsManagementLogic() {
	}

	@Autowired
	public ReservationsManagementLogic(RestaurantTableService tableService, ReservationService reservationService) {
		this.tableService = tableService;
		this.reservationService = reservationService;
	}

	public ResponseToReservationRequest startReservation(ReservationForm reservationForm) {
		// TODO Auto-generated method stub
		// sprawdzenie czy na daną datę i godzinę w danym stoliku istnieje rezerwacja
		RestaurantTable table = tableService.get(reservationForm.getTableNumber());
		List<Reservation> reservations = table.getReservationsFromDate(reservationForm.getDate());
		
		
		if (ComparisonOfReservationsHours.checkIfItIsFreeTime(reservations, reservationForm)) {
			Reservation reservation = new Reservation(reservationForm);
			reservation.setTable(table);
			table.addReservation(reservation);
			tableService.update(table.getId(), table);
			reservationService.save(reservation);
		} else {
			// jeżeli jest zajęte to szuka wolnego stolika z wystarczającą liczbą miejsc na daną godzinę
			// podaje też wolne godziny +- 2h na chcianym i innych stolikach - póki co opcjlonalne
		}
		
		
		
		return response;
	}
	
}
