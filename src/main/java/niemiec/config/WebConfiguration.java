package niemiec.config;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import niemiec.formatter.DateFormatterForReservation;
import niemiec.formatter.TimeFormatterForReservation;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldType(LocalDate.class, new DateFormatterForReservation());
		registry.addFormatterForFieldType(LocalTime.class, new TimeFormatterForReservation());
	}
}
