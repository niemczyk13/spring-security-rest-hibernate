package niemiec.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import niemiec.form.RestaurantTableForm;

@Entity
@Table(name = "RESTAURANT_TABLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RestaurantTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int tableNumber;
	private int numberOfSeats;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Reservation> reservations;

	public RestaurantTable() {
		reservations = new ArrayList<Reservation>();
	}

	public RestaurantTable(@Valid RestaurantTableForm form) {
		this.tableNumber = form.getTableNumber();
		this.numberOfSeats = form.getNumberOfSeats();
		this.reservations = new ArrayList<>();
	}

	public Long getId() {
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
		List<Reservation> list = reservations.stream().filter(r -> r.getDate().equals(date)).collect(Collectors.toList());
		if (Optional.ofNullable(reservations).isPresent()) return list;
		return new ArrayList<>();
	}

	public void deleteReservationsToDate(LocalDate date) {
		reservations = reservations.stream().filter(r -> r.getDate().compareTo(date) >= 0).collect(Collectors.toList());
	}

	public void deleteOneReservation(long reservationId) {
		reservations = reservations.stream().filter(r -> r.getId() != reservationId).collect(Collectors.toList());
	}
}
