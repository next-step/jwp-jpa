package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    Optional<LineStation> findByLine(Line line);
}
