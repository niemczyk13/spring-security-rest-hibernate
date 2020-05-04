package niemiec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import niemiec.logic.reservation.ReservationsManagementLogicForEmployee;
import niemiec.response.ResponseToEmployeeReservationRequest;

@CrossOrigin
@RestController
public class EmployeeReservationController {
	private ReservationsManagementLogicForEmployee logic;
	private ResponseToEmployeeReservationRequest response;
	
	public EmployeeReservationController() {
	}

	@Autowired
	public EmployeeReservationController(ReservationsManagementLogicForEmployee logic) {
		this.logic = logic;
	}

	@GetMapping("/reservations/{id}")
	public ResponseEntity<?> getReservation(@PathVariable Long id) {
		response = logic.getReservation(id);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@GetMapping("/reservations")
	public ResponseEntity<?> getReservations() {
		response = logic.getReservations();
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
		response = logic.deleteReservation(id);
		return new ResponseEntity<>(response.getHttpStatus());
	}
}