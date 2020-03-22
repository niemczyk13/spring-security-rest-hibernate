package niemiec.controller.validation.restaurantTable;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import niemiec.form.RestaurantTableForm;
import niemiec.model.RestaurantTable;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class RestaurantTableFormValidator {
	
	@Autowired
	private RestaurantTableService restaurantTableService;
	
	private int tableNumber;
	private Errors errors;

	public void validate(RestaurantTableForm form, Errors errors) {
		createStartupVariables(form, errors);
		checkIfTheGivenNumberExistsInTheDatabase();
	}

	private void checkIfTheGivenNumberExistsInTheDatabase() {
		RestaurantTable restaurantTable;
		restaurantTable = restaurantTableService.getByTableNumber(tableNumber);
		if (restaurantTableExist(restaurantTable)) {
			String message = "Table number " + tableNumber + " exists";
			errors.rejectValue("tableNumber", message, message);
		}
	}

	private boolean restaurantTableExist(RestaurantTable restaurantTable) {
		return Optional.ofNullable(restaurantTable).isPresent();
	}

	private void createStartupVariables(RestaurantTableForm form, Errors errors) {
		this.tableNumber = form.getTableNumber();
		this.errors = errors;
	}

}
