package niemiec.dao.table;

import java.util.List;

import niemiec.model.Table;
import niemiec.repository.TableRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class TableDAOImpl implements TableDAO {
	
	@Autowired
	private TableRepository tableRepository;

	@Override
	public List<Table> list() {
		return tableRepository.findAll();
	}

	@Override
	public Table get(long id) {
		return tableRepository.getOne(id);
	}

	@Override
	public long save(Table table) {
		return tableRepository.save(table).getId();
	}

	@Override
	public void update(long id, Table table) {
		table.setId(id);
		tableRepository.save(table);
	}

	@Override
	public void delete(long id) {
		tableRepository.deleteById(id);
	}
}
