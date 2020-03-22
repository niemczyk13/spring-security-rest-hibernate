package niemiec.logic.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	private ReservationForm reservationForm;
	private ResponseToReservationRequest response;
	private RestaurantTable restaurantTable;
	private List<Reservation> reservations;
	private List<RestaurantTable> tables;
	private Reservation reservation;
	private TimeIntervals timeIntervals;

	public ReservationsManagementLogic() {
	}

	@Autowired
	public ReservationsManagementLogic(RestaurantTableService tableService, ReservationService reservationService,
			ComparisonOfReservationsHours comparison) {
		this.tableService = tableService;
		this.reservationService = reservationService;
		this.comparison = comparison;
	}

	public ResponseToReservationRequest startReservation(ReservationForm reservationForm) {
		createStartupVariables(reservationForm);
		return tryToMakeAReservation();
	}

	private ResponseToReservationRequest tryToMakeAReservation() {
		if (reservationTimeIsFree()) {
			return createOkResponseF();
		}
		
		return createNotFoundResponse();
	}

	private ResponseToReservationRequest createNotFoundResponse() {
		findFreeTables();
		findTimeIntervals();
		addDataToResponseNotFound();
		
		return response;
	}

	private void addDataToResponseNotFound() {
		response.setRestaurantTables(tables);
		response.setTimeIntervals(timeIntervals);
		response.setHttpStatus(HttpStatus.NOT_FOUND);
	}

	private void findTimeIntervals() {
		timeIntervals = findFreeHoursInTable(restaurantTable, reservationForm.getDate());
	}

	private void findFreeTables() {
		tables = tableService.getByNumberOfSeatsGreaterThanEqual(reservationForm.getNumberOfPeople());
		tables = findTablesWithFreeTime(tables, reservationForm);
	}

	private ResponseToReservationRequest createOkResponseF() {
		createReservation();
		addReservationToDatabase();
		addDataToResponseOK();
		
		return response;
	}

	private void addDataToResponseOK() {
		response.setReservation(reservation);
		response.setHttpStatus(HttpStatus.OK);
	}

	private void addReservationToDatabase() {
		reservationService.save(reservation);
		tableService.update(restaurantTable.getId(), restaurantTable);
	}

	private boolean reservationTimeIsFree() {
		return reservations.isEmpty() || comparison.checkIfItIsFreeTime(reservations, reservationForm);
	}

	private void createStartupVariables(ReservationForm reservationForm) {
		this.reservationForm = reservationForm;
		this.response = new ResponseToReservationRequest();
		this.restaurantTable = tableService.getByTableNumber(reservationForm.getTableNumber());
		this.reservations = restaurantTable.getReservationsFromDate(reservationForm.getDate());
	}

	private void createReservation() {
		reservation = new Reservation(reservationForm);
		reservation.setRestaurantTable(restaurantTable);
		restaurantTable.addReservation(reservation);
	}

	private TimeIntervals findFreeHoursInTable(RestaurantTable table, LocalDate date) {
		return comparison.findFreeTimesInTable(table.getReservations(), date);
	}

	private List<RestaurantTable> findTablesWithFreeTime(List<RestaurantTable> tables,
			ReservationForm reservationForm) {
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
