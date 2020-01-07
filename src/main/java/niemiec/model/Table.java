package niemiec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
	private long id;
	private int number;
	private int numberOfSeats;
	private List<Reservation> reservations;

	public Table() {
		reservations = new ArrayList<Reservation>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public void addReservation(Reservation reservation) {
		this.reservations.add(reservation);
	}
	
	public void deleteReservation(long reservationId) {
		this.reservations = this.reservations.stream()
				.filter(r -> r.getId() != reservationId)
				.collect(Collectors.toList());
	}
}
