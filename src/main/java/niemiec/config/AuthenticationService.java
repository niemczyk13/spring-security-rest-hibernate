package niemiec.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import niemiec.model.Employee;
import niemiec.service.employee.EmployeeService;

@Service
public class AuthenticationService implements UserDetailsService {

	private EmployeeService employeeService;

	@Autowired
	public AuthenticationService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Employee employee = employeeService.get(Long.parseLong(id));
		if (employee == null) {
			throw new UsernameNotFoundException(id);
		}
		
		GrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());
		UserDetails userDetails = (UserDetails) new User(Long.toString(employee.getId()), employee.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
	
	

}
