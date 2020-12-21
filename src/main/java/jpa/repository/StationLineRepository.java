package jpa.repository;

import jpa.entity.Line;
import jpa.entity.Station;
import jpa.entity.StationLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationLineRepository extends JpaRepository<StationLine, Long> {
    List<StationLine> findByStation(Station station);
    List<StationLine> findByLine(Line line);
}
