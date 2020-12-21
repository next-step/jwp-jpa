package jpa.repository;

import java.util.List;
import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {

    List<LineStation> findByLine(Line line);

    List<LineStation> findByStation(Station station);
}
