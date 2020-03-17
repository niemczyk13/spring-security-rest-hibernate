package niemiec.logic.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.comparisionHours.ComparisonOfReservationsHours;
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
			Reservation reservation = createReservation(reservationForm, table);
			
			response.setReservation(reservation);
			response.setHttpStatus(HttpStatus.OK);
		} else {
			List<RestaurantTable> tables = tableService.getByNumberOfSeatsGreaterThanEqual(reservationForm.getNumberOfPeople());
			
			tables = findTablesWithFreeTime(tables, reservationForm);
			TimeIntervals timeIntervals = findFreeHoursInTable(table, reservationForm.getDate());
			
			response.setRestaurantTables(tables);
			response.setTimeIntervals(timeIntervals);
			response.setHttpStatus(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	private Reservation createReservation(ReservationForm reservationForm, RestaurantTable table) {
		// TODO Auto-generated method stub
		Reservation reservation = new Reservation(reservationForm);
		reservation.setRestaurantTable(table);
		table.addReservation(reservation);
		reservationService.save(reservation);
		tableService.update(table.getId(), table);
		return reservation;
	}

	private TimeIntervals findFreeHoursInTable(RestaurantTable table, LocalDate date) {
		return comparison.findFreeTimesInTable(table.getReservations(), date);
	}

	private List<RestaurantTable> findTablesWithFreeTime(List<RestaurantTable> tables, ReservationForm reservationForm) {
		List<RestaurantTable> list = new ArrayList<>();
		for (RestaurantTable table : tables) {
			List<Reservation> reservations = table.getReservationsFromDate(reservationForm.getDate());
			if (comparison.checkIfItIsFreeTime(reservations, reservationForm)) {
				list.add(table);
			}
		}
		return list;
	}
	
}
