package niemiec.form;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationForm {
	@Size(min = 2, message = "Pole musi zawierać minimum 2 znaki")
	private String name;
	
	@Size(min = 2, message = "Pole musi zawierać minimum 2 znaki")
	private String surname;
	
	@NotEmpty(message = "Proszę wpisać poprawny email")
	@Email(message = "Proszę wpisać poprawny email")
	private String email;
	
	@Size(min = 9, max = 9, message = "Numer telefonu powinien składać się z 9 znaków")
	@Digits(integer = 9, fraction = 0, message = "Proszę wprowadzić poprawny numer telefonu")
	private String phoneNumber;
	
	// TODO jeżeli jest ten błąd to nie wyszukuje tego stolika w dalszej walidacji
	@NotNull(message = "Proszę wprowadzić numer stolika")
	@Min(value = 1, message = "Proszę wprowadzić poprawny numer stolika")
	private int tableNumber;
	
	@NotNull(message = "Proszę wprowadzić liczbę osób")
	@Min(value = 1)
	private int numberOfPeople;
	
	@NotNull(message = "Pole nie może być puste")
	@FutureOrPresent(message = "Nie można wprowadzić daty z przeszłości")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@NotNull(message = "Pole nie może być puste")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startHour;
	
	@NotNull(message = "Pole nie może być puste")
	@DateTimeFormat(pattern = "HH:mm")
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

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
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
