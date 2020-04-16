package niemiec.logic.restaurantTable;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import niemiec.form.RestaurantTableForm;
import niemiec.model.RestaurantTable;
import niemiec.response.ResponseToRestaurantTableRequest;
import niemiec.service.restaurantTable.RestaurantTableService;

@Component
public class RestaurantTableManagementLogic {
	
	private RestaurantTableService restaurantTableService;
	
	private ResponseToRestaurantTableRequest response;
	private RestaurantTableForm restaurantTableForm;
	private RestaurantTable restaurantTable;
	private List<RestaurantTable> restaurantTables;
	
	public RestaurantTableManagementLogic() {
	}

	@Autowired
	public RestaurantTableManagementLogic(RestaurantTableService restaurantTableService) {
		this.restaurantTableService = restaurantTableService;
	}

	public ResponseToRestaurantTableRequest addRestaurantTable(RestaurantTableForm restaurantTableForm) {
		createStartupVariables(restaurantTableForm);
		createRestaurantTable();
		addRestaurantTableToDatabase();
		addDataToCreateResponse();
		
		return response;
	}

	private void createStartupVariables(RestaurantTableForm restaurantTableForm) {
		this.restaurantTableForm = restaurantTableForm;
		createStartupVariables();
	}

	private void createStartupVariables() {
		this.response = new ResponseToRestaurantTableRequest();
		this.restaurantTable = null;
		this.restaurantTables = null;
	}

	private void createRestaurantTable() {
		restaurantTable = new RestaurantTable(restaurantTableForm);
	}

	private void addRestaurantTableToDatabase() {
		Long restaurantTableId = restaurantTableService.save(restaurantTable);
		restaurantTable = restaurantTableService.get(restaurantTableId);
	}

	private void addDataToCreateResponse() {
		createResponse();
		response.setHttpStatus(HttpStatus.CREATED);
	}

	private void createResponse() {
		response.setRestaurantTable(restaurantTable);
		response.setRestaurantTables(restaurantTables);
	}

	public ResponseToRestaurantTableRequest updateRestaurantTable(RestaurantTableForm restaurantTableForm) {
		createStartupVariables(restaurantTableForm);
		updateRestaurantTableInDatabase();
		addDataToOkResponse();
		return response;
	}

	private void updateRestaurantTableInDatabase() {
		RestaurantTable oldTable = restaurantTableService.getByTableNumber(restaurantTableForm.getTableNumber());
		RestaurantTable newTable = new RestaurantTable(restaurantTableForm);
		restaurantTableService.update(oldTable.getId(), newTable);
		
		restaurantTable = restaurantTableService.get(oldTable.getId());
	}

	private void addDataToOkResponse() {
		createResponse();
		response.setHttpStatus(HttpStatus.OK);
	}

	public ResponseToRestaurantTableRequest getRestaurantTables() {
		createStartupVariables();
		getRestaurantTablesFromDatabase();
		addDataToOkResponse();
		return response;
	}

	private void getRestaurantTablesFromDatabase() {
		restaurantTables = restaurantTableService.list();
	}

	public ResponseToRestaurantTableRequest getRestaurantTableById(Long id) {
		createStartupVariables();
		getRestaurantTableByIdFromDatabase(id);
		createResponseForGetTableById(id);
		return response;
	}

	private void getRestaurantTableByIdFromDatabase(Long id) {
		restaurantTable = restaurantTableService.get(id);
	}

	private void createResponseForGetTableById(Long id) {
		if (Optional.ofNullable(restaurantTable).isPresent()) {
			addDataToOkResponse();
		} else {
			throw new EntityNotFoundException("Table with id = " + id + " does not exist.");
		}
	}

	public ResponseToRestaurantTableRequest deleteRestaurantTableById(Long id) {
		createStartupVariables();
		deleteRestaurantTableInDatabase(id);
		addDataToOkResponse();
		return response;
	}

	private void deleteRestaurantTableInDatabase(Long id) {
		restaurantTableService.delete(id);
	}
}
