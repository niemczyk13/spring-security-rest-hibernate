package niemiec.logic.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.form.TimeIntervalsForm;
import niemiec.logic.reservation.comparisionHours.ComparisonOfReservationsHours;
import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;
import niemiec.response.TimeIntervals;
import niemiec.response.reservationRequest.ResponseToReservationRequest;
import niemiec.response.reservationRequest.ResponseToTimeIntervalsRequest;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationsManagementLogic {

	private RestaurantTableService tableService;
	private ReservationService reservationService;
	private ComparisonOfReservationsHours comparison;

	private ReservationForm reservationForm;
	private LocalDate date;
	private LocalTime startHour;
	private LocalTime endHour;
	private int numberOfPeople;
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
	
	public ResponseToTimeIntervalsRequest findTimeIntervalsIfTheTimeIsNotFree(TimeIntervalsForm timeIntervalsForm) {
		createStartupVariables(timeIntervalsForm);
		// TODO
		if (restaurantTable == null) {
			System.out.println(restaurantTable);
			return new ResponseToTimeIntervalsRequest(HttpStatus.BAD_REQUEST);
		}
		if (reservationTimeIsFree()) {
			return new ResponseToTimeIntervalsRequest(HttpStatus.OK);
		}
		findTimeIntervals();
		return new ResponseToTimeIntervalsRequest(timeIntervals, HttpStatus.LOCKED);
	}

	private void createStartupVariables(TimeIntervalsForm timeIntervalsForm) {
		// TODO Auto-generated method stub
//		timeIntervalsResponse = new ResponseToTimeIntervalsRequest();
		this.date = timeIntervalsForm.getDate();
		this.startHour = timeIntervalsForm.getStartHour();
		this.endHour = timeIntervalsForm.getEndHour();
		this.restaurantTable = tableService.getByTableNumber(timeIntervalsForm.getTableNumber());
		if (restaurantTable != null)
		this.reservations = restaurantTable.getReservationsFromDate(date);
	}

	private ResponseToReservationRequest tryToMakeAReservation() {
		if (reservationTimeIsFree()) {
			return createOkResponse();
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
		timeIntervals = findFreeHoursInTable(restaurantTable, date);
	}

	private void findFreeTables() {
		tables = tableService.getByNumberOfSeatsGreaterThanEqual(numberOfPeople);
		tables = findTablesWithFreeTime(tables);
	}

	private ResponseToReservationRequest createOkResponse() {
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
		return reservations.isEmpty() || comparison.checkIfItIsFreeTime(reservations, date,
				startHour, endHour);
	}

	private void createStartupVariables(ReservationForm reservationForm) {
		this.reservationForm = reservationForm;
		this.date = reservationForm.getDate();
		this.startHour = reservationForm.getStartHour();
		this.endHour = reservationForm.getEndHour();
		this.numberOfPeople = reservationForm.getNumberOfPeople();
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

	private List<RestaurantTable> findTablesWithFreeTime(List<RestaurantTable> tables) {
		List<RestaurantTable> list = new ArrayList<>();
		for (RestaurantTable table : tables) {
			List<Reservation> reservations = table.getReservationsFromDate(date);
			if (comparison.checkIfItIsFreeTime(reservations, date,
					startHour, endHour)) {
				list.add(table);
			}
		}
		return list;
	}

}
