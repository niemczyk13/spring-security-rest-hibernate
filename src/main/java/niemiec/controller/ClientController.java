package niemiec.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.ReservationsManagementLogic;
import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;
import niemiec.response.ResponseToReservationRequest;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

@RestController
public class ClientController {

	private ReservationsManagementLogic reservationManagementLogic;
	@Autowired
	private RestaurantTableService restaurantTableService;
	@Autowired
	private ReservationService reservationService;
	
	public ClientController() {
	}

	@Autowired
	public ClientController(ReservationsManagementLogic reservationManagementLogic) {
		this.reservationManagementLogic = reservationManagementLogic;
	}


	@PostMapping("/reservations")
	public ResponseEntity<?> reservation(@Valid ReservationForm reservationForm, BindingResult bindingResult) {
		// TODO - we własnej walidacji sprawdzić czy ID TABLE nie jest za duże
		// TODO - we własnej walidacji sprawdzić czy startTime i endTime mieszczą się w granicy z RestaurantInformation
//		if (bindingResult.hasErrors()) {
//			return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
//		}
//		
//		ResponseToReservationRequest response = reservationManagementLogic.startReservation(reservationForm);
//		return new ResponseEntity<>(response, response.getHttpStatus());
		
		Reservation reservation = new Reservation(reservationForm);
		
		RestaurantTable restaurantTable = restaurantTableService.getByTableNumber(reservationForm.getTableNumber());
		reservation.setRestaurantTable(restaurantTable);
		restaurantTable.addReservation(reservation);
		reservationService.save(reservation);
		restaurantTableService.update(restaurantTable.getId(), restaurantTable);
		
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}
	
	@GetMapping("/tables/{id}")
	public ResponseEntity<?> getRestaurantTableByTableNumber(@PathVariable int id) {
		RestaurantTable restaurantTable = getRestaurantTableByIdFromService(id); 
		return new ResponseEntity<>(restaurantTable, HttpStatus.OK);
	}

	private RestaurantTable getRestaurantTableByIdFromService(int id) {
		RestaurantTable restaurantTable = restaurantTableService.get(id);
		if (restaurantTable == null) {
			throw new EntityNotFoundException("Table with id = " + id + " does not exist.");
		}
		return restaurantTable;
	}
}
