package niemiec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import niemiec.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
}
