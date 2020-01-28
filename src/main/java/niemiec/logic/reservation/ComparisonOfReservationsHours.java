package niemiec.logic.reservation;

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
	
	public static boolean checkIfTheGivenHoursAreInWorkingHours(LocalTime startHour, LocalTime endHour, LocalTime openHour, LocalTime closeHour) {
		// TODO
		LocalTime[] reservationHours = createTwoElementsTableWithLocalTime(startHour, endHour);
		LocalTime[] openingHours = createTwoElementsTableWithLocalTime(openHour, closeHour);
		if (restaurantClosesAfterMidnight(openingHours[END_HOUR])) {
			// TODO
		} else {
			// TODO
		}
		return false;
	}

	

	private static boolean restaurantClosesAfterMidnight(LocalTime hour) {
		return hour.compareTo(LocalTime.MIDNIGHT) == BIGGER;
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

	private static boolean hoursFromFormCollideWithHoursFromReservation(LocalTime[] formHours, LocalTime[] reservationHours) {
		if (startHourFromFormIsAtTheEndOrAfterExistReservation(formHours[START_HOUR], reservationHours[END_HOUR])) {
			return NO_COLLISION;
		} else if (endHourFromFormIsAtTheStartOrBeforeExistReservation(formHours[END_HOUR], reservationHours[START_HOUR])) {
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

	private static boolean startHourFromFormIsAtTheEndOrAfterExistReservation(LocalTime startHour,
			LocalTime endHour) {
		return startHour.equals(endHour) || startHour.compareTo(endHour) == BIGGER;
	}

	private static boolean endHourFromFormIsAtTheStartOrBeforeExistReservation(LocalTime endHour,
			LocalTime startHour) {
		return endHour.equals(startHour) || endHour.compareTo(startHour) == SMALLER;
	}
}
