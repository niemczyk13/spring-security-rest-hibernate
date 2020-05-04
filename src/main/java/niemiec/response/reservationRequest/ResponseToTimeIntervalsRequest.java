package niemiec.response.reservationRequest;

import org.springframework.http.HttpStatus;

import niemiec.response.TimeIntervals;

public class ResponseToTimeIntervalsRequest {
	private TimeIntervals timeIntervals;
	private HttpStatus httpStatus;

	public ResponseToTimeIntervalsRequest(HttpStatus ok) {
		// TODO Auto-generated constructor stub
		this.httpStatus = ok;
	}

	public ResponseToTimeIntervalsRequest(TimeIntervals timeIntervals, HttpStatus ok) {
		// TODO Auto-generated constructor stub
		this.timeIntervals = timeIntervals;
		this.httpStatus = ok;
	}

	public TimeIntervals getTimeIntervals() {
		return timeIntervals;
	}

	public void setTimeIntervals(TimeIntervals timeIntervals) {
		this.timeIntervals = timeIntervals;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
