package jpa.domain.linestation;

import jpa.domain.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    LineStation findByPreStation(Station station);
}
