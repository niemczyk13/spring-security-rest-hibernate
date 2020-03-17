package niemiec.logic.reservation.comparisionHours.freeTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import niemiec.logic.reservation.comparisionHours.ComparisonOfReservationsHours;
import niemiec.model.Reservation;
import niemiec.response.TimeInterval;
import niemiec.response.TimeIntervals;
import niemiec.restaurant.RestaurantInformations;

@Component
public class FreeTimesInTable {
	private final int SMALLER = -1;
	private final long ONE_DAY = 1;

	@Autowired
	private ComparisonOfReservationsHours comparison;

	private List<Reservation> reservations;
	private LocalDateTime nextFreeHour;
	private LocalDateTime closeHour;
	private TimeIntervals timeIntervals;
	private LocalTime openRestuarantHour = RestaurantInformations.OPEN_HOUR;
	private LocalTime closeRestaurantHour = RestaurantInformations.CLOSE_HOUR;

	public TimeIntervals find(List<Reservation> reservations, LocalDate date) {
		createStartupVariables(reservations, date);
		findTimeIntervals();
		return timeIntervals;
	}

	private void createStartupVariables(List<Reservation> reservations, LocalDate date) {
		this.nextFreeHour = createLocalDateTime(date, RestaurantInformations.OPEN_HOUR);
		this.closeHour = createLocalDateTime(date, RestaurantInformations.CLOSE_HOUR);
		this.timeIntervals = new TimeIntervals();
		this.reservations = sortedReservationsByStartHour(reservations);
	}

	private void findTimeIntervals() {
		for (Reservation reservation : reservations) {
			addHoursToTimeIntervals(reservation);
		}
		addHoursToTimeIntervalsIfTheyAreFreeToClose();
	}

	private LocalDateTime addHoursToTimeIntervals(Reservation reservation) {
		LocalDateTime startHour = createLocalDateTime(reservation.getDate(), reservation.getStartHour());
		LocalDate date = reservation.getDate();
		
		if (canAddTimeInterval(startHour, date)) {
			timeIntervals.addTimeInterval(createTimeInterval(nextFreeHour, startHour));
		}
		nextFreeHour = createLocalDateTime(date, reservation.getEndHour());
		return createLocalDateTime(reservation.getDate(), reservation.getEndHour());
	}

	private boolean canAddTimeInterval(LocalDateTime startHour, LocalDate date) {
		boolean hourIsFree = hourIsFree(nextFreeHour, startHour);

		LocalTime nextFreeHour = this.nextFreeHour.toLocalTime();
		LocalTime start = startHour.toLocalTime();
		long minimumResrvationTime = RestaurantInformations.MINIMUM_RESERVATION_TIME;
		boolean reservationTakesAMinimumTime = comparison.checkIfReservationLastsAMinimumTime(date, nextFreeHour, start,
				minimumResrvationTime);

		return hourIsFree && reservationTakesAMinimumTime;
	}

	private LocalDateTime createLocalDateTime(LocalDate date, LocalTime hour) {
		if (theHourIsTheNextDay(hour)) {
			date = date.plusDays(ONE_DAY);
		}

		return LocalDateTime.of(date, hour);
	}

	private boolean theHourIsTheNextDay(LocalTime hour) {
		return (hour.compareTo(openRestuarantHour) == SMALLER && hour.compareTo(closeRestaurantHour) == SMALLER)
				|| (hour.compareTo(openRestuarantHour) == SMALLER && hour.equals(closeRestaurantHour));
	}

	private List<Reservation> sortedReservationsByStartHour(List<Reservation> reservations) {
		return reservations.stream().sorted(Comparator.comparing(Reservation::getStartHour))
				.collect(Collectors.toList());
	}

	private void addHoursToTimeIntervalsIfTheyAreFreeToClose() {
		if (hourIsFree(nextFreeHour, closeHour)) {
			timeIntervals.addTimeInterval(createTimeInterval(nextFreeHour, closeHour));
		}
	}

	private TimeInterval createTimeInterval(LocalDateTime startHour, LocalDateTime endHour) {
		TimeInterval timeInterval = new TimeInterval();
		timeInterval.setStartHour(startHour);
		timeInterval.setEndHour(endHour);
		return timeInterval;
	}

	private boolean hourIsFree(LocalDateTime nextFreeHour, LocalDateTime hour) {
		return nextFreeHour.compareTo(hour) == SMALLER;
	}
}
