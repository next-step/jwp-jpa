package jpa.com.jaenyeong.domain.mapping;

import jpa.com.jaenyeong.domain.line.Line;
import jpa.com.jaenyeong.domain.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    List<LineStation> findByStation(Station station);
    List<LineStation> findByLine(Line line);
}
