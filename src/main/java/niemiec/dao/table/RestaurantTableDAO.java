package niemiec.dao.table;

import java.util.List;

import niemiec.dao.GenericDAO;
import niemiec.model.RestaurantTable;

public interface RestaurantTableDAO {
	List<RestaurantTable> list();
	RestaurantTable get(long id);
	long save(RestaurantTable table);
	void update(long id, RestaurantTable table);
	void delete(long id);
	RestaurantTable getByTableNumber(int tableNumber);
}
