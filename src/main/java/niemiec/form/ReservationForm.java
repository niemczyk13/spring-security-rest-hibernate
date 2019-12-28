package niemiec.form;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import niemiec.model.Table;

public class ReservationForm {
	@NotNull
	@Size(min = 2)
	private String name;
	@NotNull
	@Size(min = 2)
	private String surname;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min = 9, max = 9)
	private String phoneNumber;
	@NotNull
	private int tableId;
	@NotNull
	@Min(value = 1)
	private int numberOfPeople;
	@NotNull
	private LocalDate date;
	@NotNull
	private LocalTime startHour;
	@NotNull
	private LocalTime endHour;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
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
