package niemiec.service.restaurantTable;

import java.util.List;

import niemiec.model.RestaurantTable;

public interface RestaurantTableService {
	long save(RestaurantTable table);
	void update(long id, RestaurantTable table);
	void delete(long id);
	RestaurantTable get(long id);
	RestaurantTable getByTableNumber(int tableNumber);
	List<RestaurantTable> list();
}
