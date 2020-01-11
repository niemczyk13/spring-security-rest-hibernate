package niemiec.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.ReservationsManagementLogic;
import niemiec.response.ResponseToReservationRequest;
import niemiec.service.reservation.ReservationService;

@RestController
public class ClientController {

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> startPage() {
		Map<String, Object> aa = new HashMap<>();
		ArrayList<String> bb = new ArrayList<>();
		bb.add("Hej");
		bb.add("It's me");
		ArrayList<Integer> cc = new ArrayList<>();
		cc.add(1);
		cc.add(4444);

		aa.put("First", bb);
		aa.put("Second", cc);

		Person p = new Person("Arek", 27);
		aa.put("person", p);
		return new ResponseEntity<Map<String, Object>>(aa, HttpStatus.OK);
	}
	
	@Autowired
	private ReservationsManagementLogic reservationManagementLogic;

	@PostMapping("/reservation/new")
	public ResponseEntity<?> reservation(@Valid ReservationForm reservationForm, BindingResult bindingResult) {
		// TODO - we własnej walidacji sprawdzić czy ID TABLE nie jest za duże
		// TODO - we własnej walidacji sprawdzić czy startTime i endTime mieszczą się w granicy z RestaurantInformation
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		
		ResponseToReservationRequest response = reservationManagementLogic.startReservation(reservationForm);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	public class Person {
		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public Person() {
		}

		String name;
		int age;
	}
}
