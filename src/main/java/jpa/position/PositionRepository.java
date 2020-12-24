package jpa.position;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.line.Line;
import jpa.station.Station;

public interface PositionRepository extends JpaRepository<Position, Long> {

	List<Position> findByLine(Line line);

	List<Position> findByStation(Station station);
}
