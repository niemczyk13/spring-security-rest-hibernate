package niemiec.service.restaurantTable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import niemiec.dao.table.RestaurantTableDAO;
import niemiec.model.RestaurantTable;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {
	
	private RestaurantTableDAO restaurantTableDAO;
	
	public RestaurantTableServiceImpl() {
	}

	@Autowired
	public RestaurantTableServiceImpl(RestaurantTableDAO restaurantTableDAO) {
		this.restaurantTableDAO = restaurantTableDAO;
	}

	@Override
	public long save(RestaurantTable table) {
		return restaurantTableDAO.save(table);
	}

	@Override
	public void update(long id, RestaurantTable table) {
		restaurantTableDAO.update(id, table);
	}

	@Override
	public void delete(long id) {
		restaurantTableDAO.delete(id);
	}

	@Override
	public RestaurantTable get(long id) {
		return restaurantTableDAO.get(id);
	}

	@Override
	public List<RestaurantTable> list() {
		return restaurantTableDAO.list();
	}

	@Override
	public RestaurantTable getByTableNumber(int tableNumber) {
		return restaurantTableDAO.getByTableNumber(tableNumber);
	}

	@Override
	public List<RestaurantTable> getByNumberOfSeatsGreaterThanEqual(int numberOfSeats) {
		return restaurantTableDAO.getByNumberOfSeatsGreaterThanEqual(numberOfSeats);
	}
	
}
