package jpa.domain.linestation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    LineStation findByLineNameAndStationName(String lineName, String stationName);
}
