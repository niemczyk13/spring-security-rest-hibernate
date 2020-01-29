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
		// TODO Auto-generated method stub
		Duration timeBeforeClosing = Duration.ofSeconds(minReservationTimeBeforeClosing);
		Duration hoursDifference = Duration.between(startHour, closeHour);
		
		if (closingHourIsAtOrAfterMidnight(openHour, closeHour)) {
			return false;
		} else {
//			System.out.println(closeHour.compareTo(LocalTime.MIDNIGHT));
			return hoursDifference.equals(timeBeforeClosing) || hoursDifference.compareTo(timeBeforeClosing) == BIGGER;
		}
	}
	
	private static boolean closingHourIsAtOrAfterMidnight(LocalTime openHour, LocalTime closeHour) {
		boolean closingBeforeOpeningAndAfterMidnight = openHour.compareTo(closeHour) == BIGGER;
		return closingBeforeOpeningAndAfterMidnight;
	}

	public static boolean checkIfTheGivenHoursAreInWorkingHours(LocalTime startHour, LocalTime endHour,
			LocalTime openHour, LocalTime closeHour) {
		LocalTime[] reservationHours = createTwoElementsTableWithLocalTime(startHour, endHour);
		LocalTime[] openingHours = createTwoElementsTableWithLocalTime(openHour, closeHour);

		if (theReservationHoursIsBetweenTheOpeningAndClosingHours(reservationHours, openingHours)) {
			return true;
		}
		return false;
	}

	private static boolean theReservationHoursIsBetweenTheOpeningAndClosingHours(LocalTime[] reservationHours,
			LocalTime[] openingHours) {
		if (theHourIsBetweenTheOpeningAndClosingHours(reservationHours[START_HOUR], openingHours)
				&& theHourIsBetweenTheOpeningAndClosingHours(reservationHours[END_HOUR], openingHours)) {
			return true;
		}
		return false;
	}

	private static boolean theHourIsBetweenTheOpeningAndClosingHours(LocalTime hour, LocalTime[] openingHours) {
		
		boolean hourIsEqualsOrAfterOpenHour = hour.equals(openingHours[START_HOUR]) || hour.compareTo(openingHours[START_HOUR]) == BIGGER;
		boolean hourIsEqualsOrBeforeCloseHour = hour.equals(openingHours[END_HOUR]) || hour.compareTo(openingHours[END_HOUR]) == SMALLER;
		return hourIsEqualsOrAfterOpenHour || hourIsEqualsOrBeforeCloseHour;
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
