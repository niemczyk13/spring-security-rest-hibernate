package niemiec.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import niemiec.form.ReservationForm;

@Entity
@Table(name = "RESERVATION")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Client client;
	@ManyToOne
	private RestaurantTable restaurantTable;
	private int numberOfPeople;
	private LocalDate date;
	private LocalTime startHour;
	private LocalTime endHour;
	
	public Reservation() {
	}

	public Reservation(ReservationForm reservationForm) {
		client = createClient(reservationForm);
				
		numberOfPeople = reservationForm.getNumberOfPeople();
		date = reservationForm.getDate();
		startHour = reservationForm.getStartHour();
		endHour = reservationForm.getEndHour();
	}

	private Client createClient(ReservationForm reservationForm) {
		Client client = new Client();
		client.setEmail(reservationForm.getEmail());
		client.setName(reservationForm.getName());
		client.setSurname(reservationForm.getSurname());
		client.setPhoneNumber(reservationForm.getPhoneNumber());
		return client;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public RestaurantTable getRestaurantTable() {
		return restaurantTable;
	}

	public void setRestaurantTable(RestaurantTable resturantTable) {
		this.restaurantTable = resturantTable;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}

}
