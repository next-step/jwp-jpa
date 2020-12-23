package jpa.station;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {

	Station findByName(String name);

	List<Station> findByNameLike(String name);
}
