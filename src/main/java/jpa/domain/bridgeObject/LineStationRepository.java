package jpa.domain.bridgeObject;

import jpa.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    Optional<LineStation> findByLine(Line line);
    Optional<LineStation> findByDistance(Long distance);
}
