package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
	Station findByName(String name);
}
