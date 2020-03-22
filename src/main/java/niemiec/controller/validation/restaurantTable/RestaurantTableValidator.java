package niemiec.controller.validation.restaurantTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import niemiec.form.RestaurantTableForm;

@Component
public class RestaurantTableValidator implements Validator {
	@Autowired
	private RestaurantTableFormValidator validator;

	@Override
	public boolean supports(Class<?> clazz) {
		return RestaurantTableForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RestaurantTableForm form = (RestaurantTableForm) target;
		validator.validate(form, errors);
	}

}
