package niemiec.response;

import java.time.LocalDateTime;

public class TimeInterval {
	private LocalDateTime startHour;
	private LocalDateTime endHour;

	public TimeInterval() {
	}

	public TimeInterval(LocalDateTime startHour, LocalDateTime endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public LocalDateTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalDateTime startHour) {
		this.startHour = startHour;
	}

	public LocalDateTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalDateTime endHour) {
		this.endHour = endHour;
	}

}
