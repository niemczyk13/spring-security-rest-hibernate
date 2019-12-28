package niemiec.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatterForReservation implements Formatter<LocalDate> {
	public static final String NORMAL_PATTERN = "yyyy-MM-dd";

	@Override
	public String print(LocalDate object, Locale locale) {
		return DateTimeFormatter.ofPattern(NORMAL_PATTERN).format(object);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(NORMAL_PATTERN));
	}

}
