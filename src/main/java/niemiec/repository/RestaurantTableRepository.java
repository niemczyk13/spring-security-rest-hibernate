package niemiec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import niemiec.model.RestaurantTable;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
	RestaurantTable findByTableNumber(int tableNumber);
	RestaurantTable findById(long id);
}
