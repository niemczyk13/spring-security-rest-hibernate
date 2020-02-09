package niemiec.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import niemiec.model.Reservation;
import niemiec.model.RestaurantTable;

public class ResponseToReservationRequest {
	private Reservation reservation;
	private HttpStatus httpStatus;
	private List<RestaurantTable> restaurantTables;
	private TimeIntervals timeIntervals;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<RestaurantTable> getRestaurantTables() {
		return restaurantTables;
	}

	public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
		this.restaurantTables = restaurantTables;
	}

	public TimeIntervals getTimeIntervals() {
		return timeIntervals;
	}

	public void setTimeIntervals(TimeIntervals timeIntervals) {
		this.timeIntervals = timeIntervals;
	}

}
