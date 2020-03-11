package niemiec.logic.reservation;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;
import niemiec.response.TimeInterval;
import niemiec.response.TimeIntervals;
import niemiec.restaurant.RestaurantInformations;

@Component
public class ComparisonOfReservationsHours {
	private static final int SMALLER = -1;
	private static final int BIGGER = 1;
	private static final int START_HOUR = 0;
	private static final int END_HOUR = 1;
	private static final int TABLE_SIZE_WITH_HOURS = 2;
	private static final boolean THE_HOUR_IS_TAKEN = false;
	private static final boolean THE_HOUR_IS_FREE = true;
	private static final boolean NO_COLLISION = false;
	private static final boolean COLLISION = true;
	private static final long ONE_DAY = 1;

	public boolean checkIfReservationLastsAMinimumTime(LocalDate date, LocalTime startHour, LocalTime endHour,
			long minimumReservationTime) {
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(date, startHour, endHour, minimumReservationTime);
	}

	public boolean checkIfReservationTheRightTimeBeforeClosing(LocalDate date, LocalTime startHour, LocalTime closeHour,
			long minReservationTimeBeforeClosing) {

		return betweenTheHoursIsEqualsOrGreaterTimeDifference(date, startHour, closeHour,
				minReservationTimeBeforeClosing);
	}

	private boolean betweenTheHoursIsEqualsOrGreaterTimeDifference(LocalDate date, LocalTime startHour,
			LocalTime endHour, long minimumTime) {
		Duration difference;
		Duration minTime = Duration.ofSeconds(minimumTime);

		LocalDateTime start = createLocalDateTime(date, startHour);
		LocalDateTime end = createLocalDateTime(date, endHour);
		difference = Duration.between(start, end);

		return timeIsEqualsOrSmaller(minTime, difference);
	}

	public boolean checkIfTheGivenHoursAreInWorkingHours(LocalDate date, LocalTime startHour, LocalTime endHour, LocalTime openHour,
			LocalTime closeHour) {
		LocalDateTime start = createLocalDateTime(date, startHour);
		LocalDateTime end = createLocalDateTime(date, endHour);
		LocalDateTime open = createLocalDateTime(date, openHour);
		LocalDateTime close = createLocalDateTime(date, closeHour);
		
		
		boolean startHourIsBetween = theHourIsBeetweenOrEquals(start, open, close);
		boolean endHourIsBetween = theHourIsBeetweenOrEquals(end, open, close);

		return startHourIsBetween && endHourIsBetween;
	}

	public boolean checkIfItIsFreeTime(List<Reservation> reservations, ReservationForm reservationForm) {
		LocalDateTime[] formHours = createLocalDateTimeTableFromReservationForm(reservationForm);
		LocalDateTime[] reservationHours;

		for (Reservation reservation : reservations) {
			reservationHours = createLocalDateTimeTableFromReservationForm(reservation);
			if (hoursFromFormCollideWithHoursFromReservation(formHours, reservationHours)) {
				return THE_HOUR_IS_TAKEN;
			}
		}
		return THE_HOUR_IS_FREE;
	}

	private LocalDateTime[] createLocalDateTimeTableFromReservationForm(ReservationForm reservationForm) {
		LocalDate date = reservationForm.getDate();
		LocalTime startHour = reservationForm.getStartHour();
		LocalTime endHour = reservationForm.getEndHour();

		return createTwoElementsTableWithLocalDateTime(date, startHour, endHour);
	}

	private LocalDateTime[] createTwoElementsTableWithLocalDateTime(LocalDate date, LocalTime startHour,
			LocalTime endHour) {
		LocalDateTime[] reservationHours = new LocalDateTime[TABLE_SIZE_WITH_HOURS];
		reservationHours[START_HOUR] = createLocalDateTime(date, startHour);
		reservationHours[END_HOUR] = createLocalDateTime(date, endHour);
		return reservationHours;
	}

	private LocalDateTime createLocalDateTime(LocalDate date, LocalTime hour) {
		LocalTime openHour = RestaurantInformations.OPEN_HOUR;
		LocalTime closeHout = RestaurantInformations.CLOSE_HOUR;
		if ((hour.compareTo(openHour) == SMALLER && hour.compareTo(closeHout) == SMALLER) || 
				(hour.compareTo(openHour) == SMALLER && hour.equals(closeHout))) {
			date = date.plusDays(ONE_DAY);
		}

		return LocalDateTime.of(date, hour);
	}

	private LocalDateTime[] createLocalDateTimeTableFromReservationForm(Reservation reservation) {
		LocalDate date = reservation.getDate();
		LocalTime startHour = reservation.getStartHour();
		LocalTime endHour = reservation.getEndHour();

		return createTwoElementsTableWithLocalDateTime(date, startHour, endHour);
	}

	private boolean timeIsEqualsOrSmaller(Duration first, Duration second) {
		return first.equals(second) || first.compareTo(second) == SMALLER;
	}

	private boolean theHourIsBeetweenOrEquals(LocalDateTime hour, LocalDateTime open, LocalDateTime close) {
		boolean firstEquals = hour.equals(open);
		boolean secondEquals = hour.equals(close);
		boolean hourLaterFirstLimit = hour.compareTo(open) == BIGGER;
		boolean hourBeforeSecondLimit = hour.compareTo(close) == SMALLER;

		if (firstEquals || secondEquals) {
			return true;
		}
			
		return hourLaterFirstLimit && hourBeforeSecondLimit;
	}

	private boolean hoursFromFormCollideWithHoursFromReservation(LocalDateTime[] formHours,
			LocalDateTime[] reservationHours) {
		if (startHourFromFormIsAtTheEndOrAfterExistReservation(formHours[START_HOUR], reservationHours[END_HOUR])) {
			return NO_COLLISION;
		} else if (endHourFromFormIsAtTheStartOrBeforeExistReservation(formHours[END_HOUR],
				reservationHours[START_HOUR])) {
			return NO_COLLISION;
		}
		return COLLISION;
	}

	private boolean startHourFromFormIsAtTheEndOrAfterExistReservation(LocalDateTime startHour, LocalDateTime endHour) {
		return startHour.equals(endHour) || startHour.compareTo(endHour) == BIGGER;
	}

	private boolean endHourFromFormIsAtTheStartOrBeforeExistReservation(LocalDateTime endHour,
			LocalDateTime startHour) {
		return endHour.equals(startHour) || endHour.compareTo(startHour) == SMALLER;
	}

	public TimeIntervals findFreeTimesInTable(List<Reservation> reservations) {
		LocalTime nextFreeHour = RestaurantInformations.OPEN_HOUR;
		LocalTime closeHour = RestaurantInformations.CLOSE_HOUR;
		TimeIntervals timeIntervals = new TimeIntervals();

		reservations = sortedReservationsByStartHour(reservations);

		for (Reservation reservation : reservations) {
			addHoursToTimeIntervalsIfTheyAreFree(nextFreeHour, reservation, timeIntervals);
		}
		addHoursToTimeIntervalsIfTheyAreFreeToClose(nextFreeHour, closeHour, timeIntervals);

		return timeIntervals;
	}

	private void addHoursToTimeIntervalsIfTheyAreFree(LocalTime nextFreeHour, Reservation reservation,
			TimeIntervals timeIntervals) {
		LocalTime startHour = reservation.getStartHour();

		if (hourIsFree(nextFreeHour, startHour)) {
			timeIntervals.addTimeInterval(createTimeInterval(nextFreeHour, startHour));
			nextFreeHour = reservation.getEndHour();
		}
	}

	private void addHoursToTimeIntervalsIfTheyAreFreeToClose(LocalTime nextFreeHour, LocalTime closeHour,
			TimeIntervals timeIntervals) {
		if (hourIsFree(nextFreeHour, closeHour)) {
			timeIntervals.addTimeInterval(createTimeInterval(nextFreeHour, closeHour));
		}
	}

	private TimeInterval createTimeInterval(LocalTime nextFreeHour, LocalTime startHour) {
		TimeInterval timeInterval = new TimeInterval();
		timeInterval.setStartHour(nextFreeHour);
		timeInterval.setEndHour(startHour);
		return timeInterval;
	}

	private List<Reservation> sortedReservationsByStartHour(List<Reservation> reservations) {
		return reservations.stream().sorted(Comparator.comparing(Reservation::getStartHour))
				.collect(Collectors.toList());
	}

	private boolean hourIsFree(LocalTime nextFreeHour, LocalTime hour) {
		return !nextFreeHour.equals(hour);
	}

}
