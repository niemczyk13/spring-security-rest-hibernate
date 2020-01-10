package niemiec.service.table;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import niemiec.dao.GenericDAO;
import niemiec.model.Table;

@Service
public class TableServiceImpl implements TableService {
	
	private GenericDAO<Table> tableDAO;
	
	public TableServiceImpl() {
	}

	@Autowired
	public TableServiceImpl(GenericDAO<Table> tableDAO) {
		this.tableDAO = tableDAO;
	}

	@Override
	public long save(Table table) {
		return tableDAO.save(table);
	}

	@Override
	public void update(long id, Table table) {
		tableDAO.update(id, table);
	}

	@Override
	public void delete(long id) {
		tableDAO.delete(id);
	}

	@Override
	public Table get(long id) {
		return tableDAO.get(id);
	}

	@Override
	public List<Table> list() {
		return tableDAO.list();
	}
	
}
