package niemiec.logic.reservation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;
import niemiec.response.ResponseToReservationRequest;
import niemiec.response.TimeIntervals;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationsManagementLogic {
	
	private RestaurantTableService tableService;
	private ReservationService reservationService;
	private ComparisonOfReservationsHours comparison;
	
	
	public ReservationsManagementLogic() {
	}

	@Autowired
	public ReservationsManagementLogic(RestaurantTableService tableService, ReservationService reservationService
			, ComparisonOfReservationsHours comparison) {
		this.tableService = tableService;
		this.reservationService = reservationService;
		this.comparison = comparison;
	}

	public ResponseToReservationRequest startReservation(ReservationForm reservationForm) {
		// TODO Auto-generated method stub
		// sprawdzenie czy na daną datę i godzinę w danym stoliku istnieje rezerwacja
		ResponseToReservationRequest response = new ResponseToReservationRequest();
		RestaurantTable table = tableService.getByTableNumber(reservationForm.getTableNumber());
		List<Reservation> reservations = table.getReservationsFromDate(reservationForm.getDate());
		
		if (reservations.isEmpty() || comparison.checkIfItIsFreeTime(reservations, reservationForm)) {
			Reservation reservation = new Reservation(reservationForm);
			reservation.setRestaurantTable(table);
			table.addReservation(reservation);
			reservationService.save(reservation);
			tableService.update(table.getId(), table);
			response.setReservation(reservation);
			response.setHttpStatus(HttpStatus.OK);
			//stwórz pozytywną odpowiedz
		} else {
			// jeżeli jest zajęte to szuka wolnego stolika z wystarczającą liczbą miejsc na daną godzinę
			// podaje też wolne godziny +- 2h na chcianym i innych stolikach - póki co opcjlonalne
			List<RestaurantTable> tables = tableService.getByNumberOfSeatsGreaterThanEqual(reservationForm.getNumberOfPeople());
			// mamy tables, teraz szukamy po rezerwacjach tych tables wolnego terminu
			tables = findTablesWithFreeTime(tables, reservationForm);
			
			// wypisać wolne godziny dla danego stolika
			TimeIntervals timeIntervals = findFreeHoursInTable(table);
			
			System.out.println(tables);
			response.setRestaurantTables(tables);
			response.setTimeIntervals(timeIntervals);
			response.setHttpStatus(HttpStatus.OK);
		}
		
		return response;
	}

	private TimeIntervals findFreeHoursInTable(RestaurantTable table) {
		return comparison.findFreeTimesInTable(table.getReservations());
	}

	private List<RestaurantTable> findTablesWithFreeTime(List<RestaurantTable> tables, ReservationForm reservationForm) {
		// TODO Auto-generated method stub
		List<RestaurantTable> list = new ArrayList<>();
		for (RestaurantTable table : tables) {
			if (comparison.checkIfItIsFreeTime(table.getReservations(), reservationForm)) {
				list.add(table);
			}
		}
		return list;
	}
	
}
