package jpa.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
	Optional<LineStation> findByLineAndPreviousStation(Line line, Station previousStation);
}
