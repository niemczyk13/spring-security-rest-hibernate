package niemiec.formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class TimeFormatterForReservation implements Formatter<LocalTime> {
	public static final String NORMAL_PATTERN = "HH:mm";
	
	@Override
	public String print(LocalTime object, Locale locale) {
		return DateTimeFormatter.ofPattern(NORMAL_PATTERN).format(object);
	}

	@Override
	public LocalTime parse(String text, Locale locale) throws ParseException {
		return LocalTime.parse(text, DateTimeFormatter.ofPattern(NORMAL_PATTERN));
	}

}
