package niemiec.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RestaurantTableForm {
	@NotNull
	@Min(value = 1)
	private int tableNumber;
	@NotNull
	@Min(value = 2)
	private int numberOfSeats;

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int number) {
		this.tableNumber = number;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

}
