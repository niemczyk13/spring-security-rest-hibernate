package niemiec.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import niemiec.controller.validation.reservation.ReservationValidator;
import niemiec.form.ReservationForm;
import niemiec.form.TimeIntervalsForm;
import niemiec.logic.reservation.ReservationsManagementLogic;
import niemiec.response.reservationRequest.ResponseToReservationRequest;
import niemiec.response.reservationRequest.ResponseToTimeIntervalsRequest;

@CrossOrigin
@RestController
public class ClientController {

	private ReservationValidator reservationValidator;
	private ReservationsManagementLogic reservationManagementLogic;

	public ClientController() {
	}

	@Autowired
	public ClientController(ReservationValidator reservationValidator, 
			ReservationsManagementLogic reservationManagementLogic) {
		this.reservationValidator = reservationValidator;
		this.reservationManagementLogic = reservationManagementLogic;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(reservationValidator);
	}

	@PostMapping("/reservations")
	public ResponseEntity<?> reservation(@Valid ReservationForm reservationForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		ResponseToReservationRequest response = reservationManagementLogic.startReservation(reservationForm);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@PostMapping("/resevations/freehours")
	public ResponseEntity<?> freeHours(TimeIntervalsForm timeIntervalsForm) {
		ResponseToTimeIntervalsRequest response = reservationManagementLogic.findTimeIntervalsIfTheTimeIsNotFree(timeIntervalsForm);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
}