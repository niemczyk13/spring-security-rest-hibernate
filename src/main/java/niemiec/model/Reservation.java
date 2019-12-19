package niemiec.model;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

public class Reservation {
	private long id;
	private Set<Table> tables;
	private int numberOfPeople;
	private Date date;
	private Time startHour;
	private Time endHour;

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public Set<Table> getTables() {
		return tables;
	}

	public void setTables(Set<Table> tables) {
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

	public Time getStartHour() {
		return startHour;
	}

	public void setStartHour(Time startHour) {
		this.startHour = startHour;
	}

	public Time getEndHour() {
		return endHour;
	}

	public void setEndHour(Time endHour) {
		this.endHour = endHour;
	}

	
	public void addTable(Table table) {
		tables.add(table);
	}
}
