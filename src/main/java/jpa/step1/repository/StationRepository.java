package jpa.step1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.step1.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
}
