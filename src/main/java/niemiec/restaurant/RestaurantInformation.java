package niemiec.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RestaurantInformation {
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	public static final LocalTime openHour = LocalTime.parse("14:00", timeFormatter);
	public static final LocalTime closeHour = LocalTime.parse("23:00", timeFormatter);
}
