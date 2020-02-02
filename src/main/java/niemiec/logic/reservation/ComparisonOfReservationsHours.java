package niemiec.logic.reservation;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import niemiec.form.ReservationForm;
import niemiec.model.Reservation;

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

	public static boolean checkIfReservationLastsAMinimumTime(LocalTime startHour, LocalTime endHour,
			long minimumReservationTime) {
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(startHour, endHour, minimumReservationTime);
	}

	public static boolean checkIfReservationTheRightTimeBeforeClosing(LocalTime startHour,
			LocalTime closeHour, long minReservationTimeBeforeClosing) {
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(startHour, closeHour, minReservationTimeBeforeClosing);
	}
	
	private static boolean betweenTheHoursIsEqualsOrGreaterTimeDifference(LocalTime startHour, LocalTime endHour, long minimumTime) {
		Duration difference;
		Duration minTime = Duration.ofSeconds(minimumTime);
		
		if (hoursAreOnTheBorderOfDays(startHour, endHour)) {
			difference = calculateTheTimeDifferenceBetweenDays(startHour, endHour);
		} else {
			difference = Duration.between(startHour, endHour);
		}
		return timeIsEqualsOrSmaller(minTime, difference);
	}

	public static boolean checkIfTheGivenHoursAreInWorkingHours(LocalTime startHour, LocalTime endHour,
			LocalTime openHour, LocalTime closeHour) {
		boolean startHourIsBetween = theHourIsBeetweenOrEquals(startHour, openHour, closeHour);
		boolean endHourIsBetween = theHourIsBeetweenOrEquals(endHour, openHour, closeHour);
	
		return startHourIsBetween && endHourIsBetween;
	}

	public static boolean checkIfItIsFreeTime(List<Reservation> reservations, ReservationForm reservationForm) {
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

	private static boolean hoursAreOnTheBorderOfDays(LocalTime startHour, LocalTime endHour) {
		return startHour.compareTo(endHour) == BIGGER;
	}

	private static Duration calculateTheTimeDifferenceBetweenDays(LocalTime startHour, LocalTime endHour) {
		Duration first = Duration.between(startHour, LocalTime.MIDNIGHT);
		Duration oneDay = Duration.ofSeconds(ONE_DAY_IN_SECONDS);
		first = first.plus(oneDay);
		
		Duration second = Duration.between(LocalTime.MIDNIGHT, endHour);
		
		return first.plus(second);
	}

	private static boolean timeIsEqualsOrSmaller(Duration first, Duration second) {
		return first.equals(second) || first.compareTo(second) == SMALLER;
	}

	private static boolean theHourIsBeetweenOrEquals(LocalTime hour, LocalTime firstLimit, LocalTime secondLimit) {
		boolean firstEquals = hour.equals(firstLimit);
		boolean secondEquals = hour.equals(secondLimit);

		boolean firstComapreTo = hour.compareTo(firstLimit) == BIGGER;
		boolean seconCompareTo = hour.compareTo(secondLimit) == SMALLER;

		if (hoursAreOnTheBorderOfDays(firstLimit, secondLimit)) {
			return (firstEquals || secondEquals) || firstComapreTo;
		}
		return (firstEquals || secondEquals) || (firstComapreTo && seconCompareTo);
	}

	private static boolean hoursFromFormCollideWithHoursFromReservation(LocalTime[] formHours,
			LocalTime[] reservationHours) {
		if (startHourFromFormIsAtTheEndOrAfterExistReservation(formHours[START_HOUR], reservationHours[END_HOUR])) {
			return NO_COLLISION;
		} else if (endHourFromFormIsAtTheStartOrBeforeExistReservation(formHours[END_HOUR],
				reservationHours[START_HOUR])) {
			return NO_COLLISION;
		}
		return COLLISION;
	}

	private static LocalTime[] getHoursFromReservation(Reservation reservation) {
		return createTwoElementsTableWithLocalTime(reservation.getStartHour(), reservation.getEndHour());
	}

	private static LocalTime[] getHoursFromForm(ReservationForm reservationForm) {
		return createTwoElementsTableWithLocalTime(reservationForm.getStartHour(), reservationForm.getEndHour());
	}

	private static LocalTime[] createTwoElementsTableWithLocalTime(LocalTime startHour, LocalTime endHour) {
		LocalTime[] reservationHours = new LocalTime[TABLE_SIZE_WITH_HOURS];
		reservationHours[START_HOUR] = startHour;
		reservationHours[END_HOUR] = endHour;
		return reservationHours;
	}

	private static boolean startHourFromFormIsAtTheEndOrAfterExistReservation(LocalTime startHour, LocalTime endHour) {
		return startHour.equals(endHour) || startHour.compareTo(endHour) == BIGGER;
	}

	private static boolean endHourFromFormIsAtTheStartOrBeforeExistReservation(LocalTime endHour, LocalTime startHour) {
		return endHour.equals(startHour) || endHour.compareTo(startHour) == SMALLER;
	}

}
