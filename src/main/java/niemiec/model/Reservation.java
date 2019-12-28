package niemiec.model;

import java.time.LocalTime;
import java.util.Date;

public class Reservation {
	private long id;
	private Table tables;
	private int numberOfPeople;
	private Date date;
	private LocalTime startHour;
	private LocalTime endHour;

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public Table getTable() {
		return tables;
	}

	public void setTable(Table tables) {
		this.tables = tables;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
