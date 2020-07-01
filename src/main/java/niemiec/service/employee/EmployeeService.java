package niemiec.service.employee;

import java.util.List;

import niemiec.model.Employee;

public interface EmployeeService {
	long save(Employee employee);
	void update(long id, Employee employee);
	void delete(long id);
	Employee get(long id);
	List<Employee> list();
}
