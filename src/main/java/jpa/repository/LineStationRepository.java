package jpa.repository;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    LineStation findByStation(Station station);
    LineStation findByLine(Line line);
}
