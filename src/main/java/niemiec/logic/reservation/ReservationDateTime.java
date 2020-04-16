package niemiec.logic.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import niemiec.restaurant.RestaurantInformations;

public class ReservationDateTime {
	public static final int START_HOUR = 0;
	public static final int END_HOUR = 1;
	public static final int TABLE_SIZE_WITH_HOURS = 2;
	public static final int TABLE_SIZE_WITH_ONE_HOUR = 1;
	private static final int SMALLER = -1;
	private static final long ONE_DAY = 1;
	
	private LocalDateTime[] reservationHours;
	private LocalDateTime[] restaurantWoringTime;
	
	public ReservationDateTime(LocalDate date, LocalTime startHour, LocalTime endHour) {
		reservationHours = createTwoElementsTable(date, startHour, endHour);
		
		restaurantWoringTime = createTwoElementsTable(date, RestaurantInformations.OPEN_HOUR, 
				RestaurantInformations.CLOSE_HOUR);
	}
	
	public ReservationDateTime(LocalDate date, LocalTime startHour) {
		reservationHours = createOneElementsTable(date, startHour);
		restaurantWoringTime = createTwoElementsTable(date, RestaurantInformations.OPEN_HOUR, 
				RestaurantInformations.CLOSE_HOUR);
	}

	private LocalDateTime[] createOneElementsTable(LocalDate date, LocalTime startHour) {
		LocalDateTime[] table = new LocalDateTime[TABLE_SIZE_WITH_ONE_HOUR];
		table[START_HOUR] = createLocalDateTime(date, startHour);
		return table;
	}

	private LocalDateTime[] createTwoElementsTable(LocalDate date, LocalTime startHour, LocalTime endHour) {
		LocalDateTime[] table = new LocalDateTime[TABLE_SIZE_WITH_HOURS];
		table[START_HOUR] = createLocalDateTime(date, startHour);
		table[END_HOUR] = createLocalDateTime(date, endHour);
		return table;
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

	public LocalDateTime[] getReservationHours() {
		return reservationHours;
	}

	public void setReservationHours(LocalDateTime[] reservationHours) {
		this.reservationHours = reservationHours;
	}
	
	public LocalDateTime getStartHourAndDate() {
		return reservationHours[START_HOUR];
	}
	
	public LocalDateTime getEndHourAndDate() {
		if (reservationHours.length < TABLE_SIZE_WITH_HOURS) {
			// TODO Docelowo zgłoszenie jakegoś wyjątku
			return null;
		}
		return reservationHours[END_HOUR];
	}
	
	public LocalDateTime getOpenHourAndDate() {
		return restaurantWoringTime[START_HOUR];
	}
	
	public LocalDateTime getCloseHourAndDate() {
		return restaurantWoringTime[END_HOUR];
	}
}
