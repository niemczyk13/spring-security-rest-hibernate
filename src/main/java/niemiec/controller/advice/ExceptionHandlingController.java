package niemiec.controller.advice;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import niemiec.controller.AdminController;
import niemiec.controller.ClientController;
import niemiec.response.ExceptionResponse;

@RestController
@ControllerAdvice(assignableTypes = { AdminController.class, ClientController.class })
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exception, WebRequest request) {
		ExceptionResponse response = createExceptionResponse(exception.getMessage(), request);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception,
			WebRequest request) {
		ExceptionResponse response = createExceptionResponse(exception.getMessage(), request);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	private ExceptionResponse createExceptionResponse(String exceptionMessage, WebRequest request) {
		return new ExceptionResponse(LocalDateTime.now(), exceptionMessage,
				request.getDescription(true));
	}

}
