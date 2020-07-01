package niemiec.dao.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import niemiec.model.Employee;
import niemiec.repository.EmployeeRepository;

@Component
public class EmployeeDAOImpl implements EmployeeDAO {
	
	@Autowired
	private EmployeeRepository repository;

	@Override
	public List<Employee> list() {
		return repository.findAll();
	}

	@Override
	public Employee get(long id) {
		return repository.findById(id).get();
	}

	@Override
	public long save(Employee data) {
		return repository.save(data).getId();
	}

	@Override
	public void update(long id, Employee data) {
		data.setId(id);
		repository.save(data);
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

}
