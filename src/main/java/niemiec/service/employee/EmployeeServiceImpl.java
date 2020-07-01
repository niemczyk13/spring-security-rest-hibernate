package niemiec.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import niemiec.dao.employee.EmployeeDAO;
import niemiec.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeDAO employeeDAO;

	public EmployeeServiceImpl() {
	}

	@Autowired
	public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public long save(Employee employee) {
		return employeeDAO.save(employee);
	}

	@Override
	public void update(long id, Employee employee) {
		employeeDAO.update(id, employee);
	}

	@Override
	public void delete(long id) {
		employeeDAO.delete(id);
	}

	@Override
	public Employee get(long id) {
		return employeeDAO.get(id);
	}

	@Override
	public List<Employee> list() {
		return employeeDAO.list();
	}

}
