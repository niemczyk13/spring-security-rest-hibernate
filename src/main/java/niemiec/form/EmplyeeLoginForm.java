package niemiec.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmplyeeLoginForm {
	@NotNull(message = "Proszę podać id pracownika")
//	@Min(value = 1000)
	private Long id;
	
	@NotNull(message = "Proszę wpisać hasło")
	private String password;
}
