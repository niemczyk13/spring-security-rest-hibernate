package niemiec.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import niemiec.model.Reservation;

public class ResponseToEmployeeReservationRequest {
	private Reservation reservation;
	private List<Reservation> reservations;
	private HttpStatus httpStatus;

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
