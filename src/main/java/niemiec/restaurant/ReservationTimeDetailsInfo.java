package niemiec.restaurant;

public enum ReservationTimeDetailsInfo {
	
	MINIMUM_RESERVATION_TIME (1800L), //30min
	MINIMUM_RESERVATION_TIME_BEFORE_CLOSING (3600L); //1h
	
	private ReservationTimeDetailsInfo(long time) {
		this.time = time;
	}
	
	private final long time;
	public long time() {
		return time;
	}
}
