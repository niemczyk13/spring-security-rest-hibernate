package niemiec.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import niemiec.model.RestaurantTable;

public class ResponseToRestaurantTableRequest {
	private RestaurantTable restaurantTable;
	private HttpStatus httpStatus;
	private List<RestaurantTable> restaurantTables;

	public RestaurantTable getRestaurantTable() {
		return restaurantTable;
	}

	public void setRestaurantTable(RestaurantTable restaurantTable) {
		this.restaurantTable = restaurantTable;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public List<RestaurantTable> getRestaurantTables() {
		return restaurantTables;
	}

	public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
		this.restaurantTables = restaurantTables;
	}

}
