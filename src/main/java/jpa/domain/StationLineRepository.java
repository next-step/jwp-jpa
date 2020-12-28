package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StationLineRepository extends JpaRepository<StationLine, Long> {
    StationLine findByStationAndLine(Station station, Line line);
}
