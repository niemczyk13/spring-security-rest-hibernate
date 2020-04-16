package niemiec.logic.reservation.comparisionHours;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import niemiec.form.ReservationForm;
import niemiec.logic.reservation.ReservationDateTime;
import niemiec.logic.reservation.comparisionHours.freeTime.FreeTimesInTable;
import niemiec.model.Reservation;
import niemiec.response.TimeIntervals;
import niemiec.restaurant.RestaurantInformations;

@Component
public class ComparisonOfReservationsHours {
	private static final int SMALLER = -1;
	private static final int BIGGER = 1;
	private static final boolean THE_HOUR_IS_TAKEN = false;
	private static final boolean THE_HOUR_IS_FREE = true;
	private static final boolean NO_COLLISION = false;
	private static final boolean COLLISION = true;
	
	@Autowired
	private FreeTimesInTable findFreeTimesInTable;

	public boolean checkIfReservationLastsAMinimumTime(LocalDate date, LocalTime startHour, LocalTime endHour,
			long minimumReservationTime) {
		ReservationDateTime reservationDateTime = new ReservationDateTime(date, startHour, endHour);
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(reservationDateTime.getStartHourAndDate(),
				reservationDateTime.getEndHourAndDate(), minimumReservationTime);
	}

	public boolean checkIfReservationTheRightTimeBeforeClosing(LocalDate date, LocalTime startHour, LocalTime closeHour,
			long minReservationTimeBeforeClosing) {
		ReservationDateTime reservationDateTime = new ReservationDateTime(date, startHour);
		return betweenTheHoursIsEqualsOrGreaterTimeDifference(reservationDateTime.getStartHourAndDate(),
				reservationDateTime.getCloseHourAndDate(), minReservationTimeBeforeClosing);
	}

	private boolean betweenTheHoursIsEqualsOrGreaterTimeDifference(LocalDateTime firstHour, LocalDateTime seconHour, long minimumTime) {
		Duration difference;
		Duration minTime = Duration.ofSeconds(minimumTime);
	
		difference = Duration.between(firstHour, seconHour);
	
		return timeIsEqualsOrSmaller(minTime, difference);
	}

	private boolean timeIsEqualsOrSmaller(Duration first, Duration second) {
		return first.equals(second) || first.compareTo(second) == SMALLER;
	}

	public boolean checkIfTheGivenHoursAreInWorkingHours(LocalDate date, LocalTime startHour, LocalTime endHour) {
		
		ReservationDateTime reservationDateTime = new ReservationDateTime(date, startHour, endHour);
		
		boolean startHourIsBetween = theHourIsBeetweenOrEquals(
				reservationDateTime.getStartHourAndDate(), 
				reservationDateTime.getOpenHourAndDate(), 
				reservationDateTime.getCloseHourAndDate());
		
		boolean endHourIsBetween = theHourIsBeetweenOrEquals(
				reservationDateTime.getEndHourAndDate(), 
				reservationDateTime.getOpenHourAndDate(), 
				reservationDateTime.getCloseHourAndDate());

		return startHourIsBetween && endHourIsBetween;
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

	public boolean checkIfItIsFreeTime(List<Reservation> reservations, ReservationForm reservationForm) {
		ReservationDateTime formHours = new ReservationDateTime(reservationForm.getDate(), 
				reservationForm.getStartHour(), reservationForm.getEndHour());
		
		ReservationDateTime reservationHours;

		for (Reservation reservation : reservations) {
			reservationHours = new ReservationDateTime(reservation.getDate(), reservation.getStartHour(), reservation.getEndHour());
			if (hoursFromFormCollideWithHoursFromReservation(formHours, reservationHours)) {
				return THE_HOUR_IS_TAKEN;
			}
		}
		return THE_HOUR_IS_FREE;
	}

	private boolean hoursFromFormCollideWithHoursFromReservation(ReservationDateTime formHours,
			ReservationDateTime reservationHours) {
		if (startHourFromFormIsAtTheEndOrAfterExistReservation(formHours.getStartHourAndDate(), reservationHours.getEndHourAndDate())) {
			return NO_COLLISION;
		} else if (endHourFromFormIsAtTheStartOrBeforeExistReservation(formHours.getEndHourAndDate(),
				reservationHours.getStartHourAndDate())) {
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

	public TimeIntervals findFreeTimesInTable(List<Reservation> reservations, LocalDate date) {
		return findFreeTimesInTable.find(reservations, date);
	}

	public boolean checkIfTheTimeOfEntryIsBeforeTheTimeOfDeparture(LocalDate date, LocalTime startHour,
			LocalTime endHour) {
		ReservationDateTime reservationDateTime = new ReservationDateTime(date, startHour, endHour);
		LocalDateTime start = reservationDateTime.getStartHourAndDate();
		LocalDateTime end = reservationDateTime.getEndHourAndDate();
		return start.compareTo(end) == SMALLER;
	}
}
