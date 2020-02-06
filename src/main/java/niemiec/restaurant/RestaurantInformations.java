package niemiec.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RestaurantInformations {
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	public static final LocalTime OPEN_HOUR = LocalTime.parse("14:00", timeFormatter);
	public static final LocalTime CLOSE_HOUR = LocalTime.parse("23:00", timeFormatter);
	public static final long MINIMUM_RESERVATION_TIME = 1800L; // 30min
	public static final long MINIMUM_RESERVATION_TIME_BEFORE_CLOSING = 3600L; //1h
}