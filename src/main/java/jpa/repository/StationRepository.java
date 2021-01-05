package jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
	Optional<Station> findByName(String stationName);
}
