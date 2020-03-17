package niemiec.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;
import niemiec.service.reservation.ReservationService;
import niemiec.service.restaurantTable.RestaurantTableService;

@RestController
public class AdminController {

	private ReservationService reservationService;
	private RestaurantTableService restaurantTableService;

	public AdminController() {
	}

	// TODO DO ZROBIENIA WALIDACJA DANYCH WPROWADZANYCH PRZEZ ADMIN
	
	@Autowired
	public AdminController(ReservationService reservationService, RestaurantTableService restaurantTableService) {
		this.reservationService = reservationService;
		this.restaurantTableService = restaurantTableService;
	}

	@GetMapping("/reservations/{id}")
	public ResponseEntity<?> getReservation(@PathVariable Long id) {
		Reservation reservation = getReservationFromService(id);
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}

	@GetMapping("/reservations")
	public ResponseEntity<?> getReservations() {
		List<Reservation> reservations = reservationService.list();
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
		deleteReservationInRestaurantTable(id);
		reservationService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/tables")
	public ResponseEntity<?> addRestaurantTable(RestaurantTable restaurantTable) {
		Long restaurantTableId = restaurantTableService.save(restaurantTable);
		restaurantTable = restaurantTableService.get(restaurantTableId);
		return new ResponseEntity<>(restaurantTable, HttpStatus.CREATED);
	}

	@PutMapping("/tables")
	public ResponseEntity<?> updateRestaurantTable(RestaurantTable restaurantTable) {
		restaurantTableService.update(restaurantTable.getId(), restaurantTable);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/tables/{id}")
	public ResponseEntity<?> deleteRestaurantTableById(@PathVariable long id) {
		// TODO - informacja do wszystkich rezerwacji mailowo, że dany stolik został
		// usunięty?
		restaurantTableService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/tables")
	public ResponseEntity<?> getRestaurationTables() {
		List<RestaurantTable> restaurantTables = restaurantTableService.list();
		return new ResponseEntity<>(restaurantTables, HttpStatus.OK);
	}

	private void deleteReservationInRestaurantTable(Long id) {
		Reservation reservation = reservationService.get(id);
		RestaurantTable restaurantTable = reservation.getRestaurantTable();
		restaurantTable.deleteOneReservation(id);
		restaurantTableService.update(restaurantTable.getId(), restaurantTable);
	}

	private Reservation getReservationFromService(Long id) {
		Reservation reservation = reservationService.get(id);
		if (reservation == null) {
			throw new EntityNotFoundException("Reservation with id = " + id + " does not exist.");
		}
		return reservation;
	}
}
