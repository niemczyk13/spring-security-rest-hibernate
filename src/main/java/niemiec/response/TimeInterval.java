package niemiec.response;

import java.time.LocalTime;

public class TimeInterval {
	private LocalTime startHour;
	private LocalTime endHour;

	public TimeInterval() {
	}

	public TimeInterval(LocalTime startHour, LocalTime endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}

}
