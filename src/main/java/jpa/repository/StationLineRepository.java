package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.domain.StationLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationLineRepository extends JpaRepository<StationLine, Long> {
    StationLine findByStationAndLine(Station station, Line line);
}