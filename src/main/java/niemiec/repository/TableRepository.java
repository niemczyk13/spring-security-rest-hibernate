package niemiec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import niemiec.model.Table;

public interface TableRepository extends JpaRepository<Table, Long> {

}
