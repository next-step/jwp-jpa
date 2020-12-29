package jpa.position;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.station.Station;

public interface PositionRepository extends JpaRepository<Position, Long> {
	List<Position> findByStation(Station station);
}
