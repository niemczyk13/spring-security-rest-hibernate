package niemiec.model;

import javax.persistence.Embeddable;

@Embeddable
public class Client {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	public String getName() {
		return firstName;
	}

	public void setName(String name) {
		this.firstName = name;
	}

	public String getSurname() {
		return lastName;
	}

	public void setSurname(String surname) {
		this.lastName = surname;
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
}
