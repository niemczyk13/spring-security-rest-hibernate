package niemiec.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class TableForm {
	@NotEmpty
	@Min(1)
	private int number;
	@NotEmpty
	@Min(2)
	private int numberOfSeats;

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

}
