package niemiec.controller.validation.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import niemiec.form.ReservationForm;
import niemiec.form.TimeIntervalsForm;

@Component
public class ReservationValidator implements Validator {

	private final ReservationFormValidator reservationFormValidator;
	
	@Autowired
	public ReservationValidator(ReservationFormValidator reservationFormValidator) {
		this.reservationFormValidator = reservationFormValidator;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ReservationForm.class.isAssignableFrom(clazz) || TimeIntervalsForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof ReservationForm) {
			ReservationForm form = (ReservationForm) target;
			reservationFormValidator.validate(form, errors);
		}
	}

}
