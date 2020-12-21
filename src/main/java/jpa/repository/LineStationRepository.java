package jpa.repository;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
	List<LineStation> findByStation(Station station);

	List<LineStation> findByLocationPreviousStation(Station station);

	List<LineStation> findByLineEquals(Line line);

	LineStation findByLineNameAndStationName(String lineName, String stationName);
}
