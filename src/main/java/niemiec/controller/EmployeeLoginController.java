package niemiec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import niemiec.model.Employee;
import niemiec.service.employee.EmployeeService;

@CrossOrigin
@RestController()
public class EmployeeLoginController {
	
	@Autowired
	private EmployeeService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable Long id) {
		Employee employee = service.get(id);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}
	
	@PostMapping("/employee")
	public ResponseEntity<?> save(Employee employee) {
		String password = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(password);
		service.save(employee);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}
	
	@PutMapping("/employee")
	public ResponseEntity<?> update(long id, Employee employee) {
		String password = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(password);
		service.update(id, employee);
		return new ResponseEntity<>(service.get(id), HttpStatus.OK);
	}
}
