package niemiec.response;

import java.util.ArrayList;
import java.util.List;

public class TimeIntervals {
	List<TimeInterval> timeIntervals;
	
	public TimeIntervals() {
		timeIntervals = new ArrayList<TimeInterval>();
	}

	public List<TimeInterval> getTimeIntervals() {
		return timeIntervals;
	}

	public void setTimeIntervals(List<TimeInterval> timeIntervals) {
		this.timeIntervals = timeIntervals;
	}
	
	public void addTimeInterval(TimeInterval timeInterval) {
		timeIntervals.add(timeInterval);
	}
}
