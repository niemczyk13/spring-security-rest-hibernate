package niemiec.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import niemiec.controller.validation.restaurantTable.RestaurantTableValidator;
import niemiec.form.RestaurantTableForm;
import niemiec.logic.restaurantTable.RestaurantTableManagementLogic;
import niemiec.response.ResponseToRestaurantTableRequest;

@CrossOrigin
@RestController
public class EmployeeRestaurantTableController {

	private RestaurantTableValidator restaurantTableValidator;
	private ResponseToRestaurantTableRequest response;
	
	@Autowired
	private RestaurantTableManagementLogic restaurantTableManagementLogic;

	public EmployeeRestaurantTableController() {
	}

	@Autowired
	public EmployeeRestaurantTableController(RestaurantTableValidator restaurantTableValidator) {
		this.restaurantTableValidator = restaurantTableValidator;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(restaurantTableValidator);
	}

	@PostMapping("/tables")
	public ResponseEntity<?> addRestaurantTable(@Valid RestaurantTableForm restaurantTableForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		response = restaurantTableManagementLogic.addRestaurantTable(restaurantTableForm);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@PutMapping("/tables")
	public ResponseEntity<?> updateRestaurantTable(RestaurantTableForm restaurantTableForm) {
		response = restaurantTableManagementLogic.updateRestaurantTable(restaurantTableForm);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@DeleteMapping("/tables/{id}")
	public ResponseEntity<?> deleteRestaurantTableById(@PathVariable Long id) {
		response = restaurantTableManagementLogic.deleteRestaurantTableById(id);
		return new ResponseEntity<>(response.getHttpStatus());
	}

	@GetMapping("/tables")
	public ResponseEntity<?> getRestaurationTables() {
		response = restaurantTableManagementLogic.getRestaurantTables();
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@GetMapping("/tables/{id}")
	public ResponseEntity<?> getRestaurantTableById(@PathVariable Long id) {
		response = restaurantTableManagementLogic.getRestaurantTableById(id);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
}
