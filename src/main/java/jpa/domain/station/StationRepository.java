package jpa.domain.station;

import jpa.domain.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(String name);
}
