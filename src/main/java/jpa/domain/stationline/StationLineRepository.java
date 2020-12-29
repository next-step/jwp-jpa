package jpa.domain.stationline;

import jpa.domain.line.Line;
import jpa.domain.station.Station;
import jpa.domain.stationline.StationLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationLineRepository extends JpaRepository<StationLine, Long> {
    StationLine findByStationAndLine(Station station, Line line);
}
