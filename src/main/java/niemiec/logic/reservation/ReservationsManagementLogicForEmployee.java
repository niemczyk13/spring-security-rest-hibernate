package niemiec.logic.reservation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;
import niemiec.response.ResponseToEmployeeReservationRequest;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class ReservationsManagementLogicForEmployee {

	private RestaurantTableService restaurantTableService;
	private ReservationService reservationService;
	
	private ResponseToEmployeeReservationRequest response;
	private Reservation reservation;
	private List<Reservation> reservations;
	
	@Autowired
	public ReservationsManagementLogicForEmployee(RestaurantTableService restaurantTableService, 
			ReservationService reservationService) {
		this.restaurantTableService = restaurantTableService;
		this.reservationService = reservationService;
	}
	
	public ResponseToEmployeeReservationRequest getReservation(Long id) {
		createStartupVariables();
		getReservationFromDatabase(id);
		createOkResponse();
		return response;
	}

	private void createStartupVariables() {
		this.response = new ResponseToEmployeeReservationRequest();
		this.reservation = null;
		this.reservations = null;
	}

	private void getReservationFromDatabase(Long id) {
		reservation = getReservationFromService(id);
	}

	private Reservation getReservationFromService(Long id) {
		Reservation reservation = reservationService.get(id);
		if (Optional.ofNullable(reservation).isPresent()) {
			return reservation;
		} else {
			throw new EntityNotFoundException("Reservation with id = " + id + " does not exist.");
		}
	}

	private void createOkResponse() {
		response.setReservations(reservations);
		response.setReservation(reservation);
		response.setHttpStatus(HttpStatus.OK);
	}

	public ResponseToEmployeeReservationRequest getReservations() {
		createStartupVariables();
		getReservationsFromDatabase();
		createOkResponse();
		return response;
	}

	private void getReservationsFromDatabase() {
		reservations = reservationService.list();
	}

	public ResponseToEmployeeReservationRequest deleteReservation(Long id) {
		createStartupVariables();
		deleteReservationInRestaurantTable(id);
		deleteReservationInDatabase(id);
		createOkResponse();
		return response;
	}
	
	private void deleteReservationInRestaurantTable(Long id) {
		Reservation reservation = reservationService.get(id);
		RestaurantTable restaurantTable = reservation.getRestaurantTable();
		restaurantTable.deleteOneReservation(id);
		restaurantTableService.update(restaurantTable.getId(), restaurantTable);
	}

	private void deleteReservationInDatabase(Long id) {
		reservationService.delete(id);
	}
}
