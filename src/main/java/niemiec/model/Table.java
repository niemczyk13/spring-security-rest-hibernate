package niemiec.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
	//id is a tableNumber
	private long id;
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
	
	public List<Reservation> getReservationsFromDate(LocalDate date) {
		return reservations.stream()
				.filter(r -> r.getDate().equals(date))
				.collect(Collectors.toList());
	}
	
	public void deleteReservationsToDate(LocalDate date) {
		reservations = reservations.stream()
				.filter(r -> r.getDate().compareTo(date) >= 0)
				.collect(Collectors.toList());
	}
	
	public void deleteOneReservation(long reservationId) {
		reservations = reservations.stream()
				.filter(r -> r.getId() != reservationId)
				.collect(Collectors.toList());
	}
}
