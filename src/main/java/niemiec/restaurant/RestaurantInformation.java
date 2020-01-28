package niemiec.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RestaurantInformation {
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	public static final LocalTime OPEN_HOUR = LocalTime.parse("14:00", timeFormatter);
	public static final LocalTime CLOSE_HOUR = LocalTime.parse("23:00", timeFormatter);
	public static final LocalTime MINIMUM_RESERVATION_TIME = LocalTime.parse("00:30", timeFormatter);
}
