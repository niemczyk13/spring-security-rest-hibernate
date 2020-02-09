package niemiec.dao.table;

import java.util.List;

import niemiec.model.RestaurantTable;
import niemiec.repository.RestaurantTableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableDAOImpl implements RestaurantTableDAO {
	
	@Autowired
	private RestaurantTableRepository tableRepository;

	@Override
	public List<RestaurantTable> list() {
		return tableRepository.findAll();
	}

	@Override
	public RestaurantTable get(long id) {
		return tableRepository.findById(id);
	}

	@Override
	public long save(RestaurantTable table) {
		return tableRepository.save(table).getId();
	}

	@Override
	public void update(long id, RestaurantTable table) {
		table.setId(id);
		tableRepository.save(table);
	}

	@Override
	public void delete(long id) {
		tableRepository.deleteById(id);
	}

	@Override
	public RestaurantTable getByTableNumber(int tableNumber) {
		return tableRepository.findByTableNumber(tableNumber);
	}

	@Override
	public List<RestaurantTable> getByNumberOfSeatsGreaterThanEqual(int numberOfSeats) {
		return tableRepository.findByNumberOfSeatsGreaterThanEqual(numberOfSeats);
	}
}
