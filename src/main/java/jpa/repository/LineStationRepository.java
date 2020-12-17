package jpa.repository;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
	List<LineStation> findByStationEquals(Station station);
	List<LineStation> findByLineEquals(Line line);

	void deleteByStation(Station station);
}
