package niemiec.response;

import java.time.LocalDateTime;

public class ExceptionResponse {
	private LocalDateTime date;
	private String message;
	private String description;

	public ExceptionResponse(LocalDateTime date, String message, String description) {
		this.date = date;
		this.message = message;
		this.description = description;
	}

	public LocalDateTime getNow() {
		return date;
	}

	public void setNow(LocalDateTime now) {
		this.date = now;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
