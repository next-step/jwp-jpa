package jpa.domain.station;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(String name);

    @EntityGraph(attributePaths = "lineStations")
    Station findLineStationsEntityGraphByName(String name);
}
