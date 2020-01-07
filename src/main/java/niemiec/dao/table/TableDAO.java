package niemiec.dao.table;

import java.util.List;

import niemiec.dao.GenericDAO;
import niemiec.model.Table;

public interface TableDAO extends GenericDAO<Table> {
	List<Table> list();
	Table get(long id);
	long save(Table table);
	void update(long id, Table table);
	void delete(long id);
}
