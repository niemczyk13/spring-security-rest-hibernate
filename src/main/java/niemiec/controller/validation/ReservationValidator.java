package niemiec.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import niemiec.form.ReservationForm;

@Component
public class ReservationValidator implements Validator {

	@Autowired
	private ReservationFormValidator reservationFormValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return ReservationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ReservationForm form = (ReservationForm) target;
		reservationFormValidator.validate(form, errors);
	}

}
