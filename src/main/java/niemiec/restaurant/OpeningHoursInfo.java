package niemiec.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public enum OpeningHoursInfo {
	
	OPEN("14:00"), CLOSE("01:00");
	
	private final LocalTime hour;
	private final DateTimeFormatter timeFormatter;
	
	private OpeningHoursInfo(String hour) {
		this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		this.hour = LocalTime.parse(hour, timeFormatter);
	}
	
	public LocalTime hour() {
		return hour;
	}
}
