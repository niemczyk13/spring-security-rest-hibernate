package niemiec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import niemiec.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
}
