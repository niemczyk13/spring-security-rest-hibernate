package niemiec.service.table;

import java.util.List;

import niemiec.model.Table;

public interface TableService {
	long save(Table table);
	void update(long id, Table table);
	void delete(long id);
	Table get(long id);
	List<Table> list();
}
