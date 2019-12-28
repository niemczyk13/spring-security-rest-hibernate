package niemiec.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;

import niemiec.form.ReservationForm;

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

	@PostMapping("/reservation/new")
	public ResponseEntity<ReservationForm> reservation(@Valid ReservationForm reservationForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<ReservationForm>(reservationForm, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ReservationForm>(reservationForm, HttpStatus.OK);
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
