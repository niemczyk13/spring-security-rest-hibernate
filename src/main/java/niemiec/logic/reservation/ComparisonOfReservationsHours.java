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

	public static boolean checkIfReservationTheRightTimeBeforeClosing(LocalTime startHour, LocalTime openHour,
			LocalTime closeHour, long minReservationTimeBeforeClosing) {
		Duration timeBeforeClosing = Duration.ofSeconds(minReservationTimeBeforeClosing);
		Duration hoursDifference = Duration.between(startHour, closeHour);

		if (startHourIsBeforeMidnightAndCloseHoursIsAfterOrOnMidnight(startHour, openHour, closeHour)) {
			return startHourBeforeMidnightIsGood(startHour, closeHour, timeBeforeClosing, hoursDifference);
		}
		return otherStartHoursIsGood(timeBeforeClosing, hoursDifference);
	}

	private static boolean otherStartHoursIsGood(Duration timeBeforeClosing, Duration hoursDifference) {
		return hourIsEqualsOrSmaller(timeBeforeClosing, hoursDifference);
	}

	private static boolean startHourBeforeMidnightIsGood(LocalTime startHour, LocalTime closeHour,
			Duration timeBeforeClosing, Duration hoursDifference) {
		Duration startAndMidnightDifference = Duration.between(startHour, LocalTime.MIDNIGHT);
		Duration oneDay = Duration.ofSeconds(86_400L);
		startAndMidnightDifference = startAndMidnightDifference.plus(oneDay);

		Duration closeAndMidnightDifference = Duration.between(LocalTime.MIDNIGHT, closeHour);

		Duration difference = startAndMidnightDifference.plus(closeAndMidnightDifference);

		return hourIsEqualsOrSmaller(timeBeforeClosing, difference);
	}

	private static boolean hourIsEqualsOrSmaller(Duration first, Duration second) {
		return first.equals(second) || first.compareTo(second) == SMALLER;
	}

	private static boolean startHourIsBeforeMidnightAndCloseHoursIsAfterOrOnMidnight(LocalTime startHour,
			LocalTime openHour, LocalTime closeHour) {
		boolean closeHourIsAfterOrOnMidnight = theHourIsBeetweenOrEquals(closeHour, LocalTime.MIDNIGHT, openHour);
		boolean startHourIsBeforeMidnight = ifStartHourIsBeforeMidnightMustBeBiggerFromCloseHour(startHour, closeHour);

		return closeHourIsAfterOrOnMidnight && startHourIsBeforeMidnight;
	}

	private static boolean ifStartHourIsBeforeMidnightMustBeBiggerFromCloseHour(LocalTime startHour,
			LocalTime closeHour) {
		return startHour.compareTo(closeHour) == BIGGER;
	}

	public static boolean checkIfTheGivenHoursAreInWorkingHours(LocalTime startHour, LocalTime endHour,
			LocalTime openHour, LocalTime closeHour) {
		boolean startHourIsBetween = theHourIsBeetweenOrEquals(startHour, openHour, closeHour);
		boolean endHourIsBetween = theHourIsBeetweenOrEquals(endHour, openHour, closeHour);

		return startHourIsBetween && endHourIsBetween;
	}

	private static boolean theHourIsBeetweenOrEquals(LocalTime hour, LocalTime firstLimit, LocalTime secondLimit) {
		boolean firstEquals = hour.equals(firstLimit);
		boolean secondEquals = hour.equals(secondLimit);

		boolean firstComapreTo = hour.compareTo(firstLimit) == BIGGER;
		boolean seconCompareTo = hour.compareTo(secondLimit) == SMALLER;

		if (closeHourIsAfterMidnight(firstLimit, secondLimit)) {
			return (firstEquals || secondEquals) || firstComapreTo;
		}
		return (firstEquals || secondEquals) || (firstComapreTo && seconCompareTo);
	}

	private static boolean closeHourIsAfterMidnight(LocalTime firstLimit, LocalTime secondLimit) {
		return secondLimit.compareTo(firstLimit) == SMALLER;
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
