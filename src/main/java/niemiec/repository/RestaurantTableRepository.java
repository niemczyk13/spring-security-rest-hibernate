package niemiec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import niemiec.model.RestaurantTable;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
	RestaurantTable findByTableNumber(int tableNumber);
	RestaurantTable findById(long id);
	List<RestaurantTable> findByNumberOfSeatsGreaterThanEqual(int numberOfSeats);
}
