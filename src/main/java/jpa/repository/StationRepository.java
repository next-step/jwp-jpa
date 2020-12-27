package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
	Station findByName(String name);
}