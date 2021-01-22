package jpa.repositories;

import jpa.domain.LineStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    LineStation findByLineNameAndStationName(String line, String station);
}
