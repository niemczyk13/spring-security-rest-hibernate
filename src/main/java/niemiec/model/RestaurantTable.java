package niemiec.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.OnDeleteAction;

import org.hibernate.annotations.OnDelete;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@javax.persistence.Table(name = "RESTAURANT_TABLE")
public class RestaurantTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int tableNumber;
	private int numberOfSeats;
	@OneToMany
	@JsonManagedReference
	private List<Reservation> reservations;

	public RestaurantTable() {
		reservations = new ArrayList<Reservation>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
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
		return reservations.stream().filter(r -> r.getDate().equals(date)).collect(Collectors.toList());
	}

	public void deleteReservationsToDate(LocalDate date) {
		reservations = reservations.stream().filter(r -> r.getDate().compareTo(date) >= 0).collect(Collectors.toList());
	}

	public void deleteOneReservation(long reservationId) {
		reservations = reservations.stream().filter(r -> r.getId() != reservationId).collect(Collectors.toList());
	}
}
