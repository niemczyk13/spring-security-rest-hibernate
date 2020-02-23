package niemiec.logic.reservation;

import java.time.Duration;
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
	private static final long ONE_DAY_IN_SECONDS = 86_400L;

	public boolean checkIfReservationLastsAMinimumTime(LocalTime startHour, LocalTime endHour,
			long minimumReservationTime) {
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(startHour, endHour, minimumReservationTime);
	}

	public boolean checkIfReservationTheRightTimeBeforeClosing(LocalTime startHour, LocalTime closeHour,
			long minReservationTimeBeforeClosing) {
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(startHour, closeHour, minReservationTimeBeforeClosing);
	}

	private boolean betweenTheHoursIsEqualsOrGreaterTimeDifference(LocalTime startHour, LocalTime endHour,
			long minimumTime) {
		Duration difference;
		Duration minTime = Duration.ofSeconds(minimumTime);

		if (hoursAreOnTheBorderOfDays(startHour, endHour)) {
			difference = calculateTheTimeDifferenceBetweenDays(startHour, endHour);
		} else {
			difference = Duration.between(startHour, endHour);
		}
		return timeIsEqualsOrSmaller(minTime, difference);
	}

	public boolean checkIfTheGivenHoursAreInWorkingHours(LocalTime startHour, LocalTime endHour, LocalTime openHour,
			LocalTime closeHour) {
		boolean startHourIsBetween = theHourIsBeetweenOrEquals(startHour, openHour, closeHour);
		boolean endHourIsBetween = theHourIsBeetweenOrEquals(endHour, openHour, closeHour);

		return startHourIsBetween && endHourIsBetween;
	}

	public boolean checkIfItIsFreeTime(List<Reservation> reservations, ReservationForm reservationForm) {
		LocalTime[] formHours = getHoursFromForm(reservationForm);
		LocalTime[] reservationHours;

		for (Reservation reservation : reservations) {
			reservationHours = getHoursFromReservation(reservation);
			if (hoursFromFormCollideWithHoursFromReservation(formHours, reservationHours)) {
				return THE_HOUR_IS_TAKEN;
			}
		}
		return THE_HOUR_IS_FREE;
	}

	private boolean hoursAreOnTheBorderOfDays(LocalTime startHour, LocalTime endHour) {
		return startHour.compareTo(endHour) == BIGGER;
	}

	private Duration calculateTheTimeDifferenceBetweenDays(LocalTime startHour, LocalTime endHour) {
		Duration first = Duration.between(startHour, LocalTime.MIDNIGHT);
		Duration oneDay = Duration.ofSeconds(ONE_DAY_IN_SECONDS);
		first = first.plus(oneDay);

		Duration second = Duration.between(LocalTime.MIDNIGHT, endHour);

		return first.plus(second);
	}

	private boolean timeIsEqualsOrSmaller(Duration first, Duration second) {
		return first.equals(second) || first.compareTo(second) == SMALLER;
	}

	private boolean theHourIsBeetweenOrEquals(LocalTime hour, LocalTime firstLimit, LocalTime secondLimit) {
		boolean firstEquals = hour.equals(firstLimit);
		boolean secondEquals = hour.equals(secondLimit);
		boolean hourLaterFirstLimit = hour.compareTo(firstLimit) == BIGGER;
		boolean hourBeforeSecondLimit = hour.compareTo(secondLimit) == SMALLER;

		if (firstEquals || secondEquals) {
			return true;
		} else if (hoursAreOnTheBorderOfDays(firstLimit, secondLimit)) {
			return hourLaterFirstLimit || hourBeforeSecondLimit;
		} else {
			return hourLaterFirstLimit && hourBeforeSecondLimit;
		}
		
	}

	private boolean hoursFromFormCollideWithHoursFromReservation(LocalTime[] formHours, LocalTime[] reservationHours) {
		if (startHourFromFormIsAtTheEndOrAfterExistReservation(formHours[START_HOUR], reservationHours[END_HOUR])) {
			return NO_COLLISION;
		} else if (endHourFromFormIsAtTheStartOrBeforeExistReservation(formHours[END_HOUR],
				reservationHours[START_HOUR])) {
			return NO_COLLISION;
		}
		return COLLISION;
	}

	private LocalTime[] getHoursFromReservation(Reservation reservation) {
		return createTwoElementsTableWithLocalTime(reservation.getStartHour(), reservation.getEndHour());
	}

	private LocalTime[] getHoursFromForm(ReservationForm reservationForm) {
		return createTwoElementsTableWithLocalTime(reservationForm.getStartHour(), reservationForm.getEndHour());
	}

	private LocalTime[] createTwoElementsTableWithLocalTime(LocalTime startHour, LocalTime endHour) {
		LocalTime[] reservationHours = new LocalTime[TABLE_SIZE_WITH_HOURS];
		reservationHours[START_HOUR] = startHour;
		reservationHours[END_HOUR] = endHour;
		return reservationHours;
	}

	private boolean startHourFromFormIsAtTheEndOrAfterExistReservation(LocalTime startHour, LocalTime endHour) {
		return startHour.equals(endHour) || startHour.compareTo(endHour) == BIGGER;
	}

	private boolean endHourFromFormIsAtTheStartOrBeforeExistReservation(LocalTime endHour, LocalTime startHour) {
		return endHour.equals(startHour) || endHour.compareTo(startHour) == SMALLER;
	}

	public TimeIntervals findFreeTimesInTable(List<Reservation> reservations) {
		LocalTime start = RestaurantInformations.OPEN_HOUR;
		TimeIntervals timeIntervals = new TimeIntervals();

		LocalTime startHour;
		LocalTime endHour;

		reservations = reservations.stream().sorted(Comparator.comparing(Reservation::getStartHour))
				.collect(Collectors.toList());
		System.out.println(reservations);
		for (Reservation reservation : reservations) {
			// TODO UWZGLĘDNIĆ OPCJĘ PO PÓŁNOCY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// if endHour < openHour
			// jeżeli tak to jeszcze sprawdzamy czy startHout < openHour
			TimeInterval timeInterval = new TimeInterval();
			startHour = reservation.getStartHour();
			endHour = reservation.getEndHour();

			if (!start.equals(startHour)) {

//				if (hoursAreOnTheBorderOfDays(start, startHour)) {
//					
//				} else {

					timeInterval.setStartHour(start);
					timeInterval.setEndHour(startHour);
					start = endHour;
					timeIntervals.addTimeInterval(timeInterval);
//				}
			}
		}
		if (!start.equals(RestaurantInformations.CLOSE_HOUR)) {
			TimeInterval timeInterval = new TimeInterval();
			timeInterval.setStartHour(start);
			timeInterval.setEndHour(RestaurantInformations.CLOSE_HOUR);
			timeIntervals.addTimeInterval(timeInterval);
		}
		return timeIntervals;
	}

}
