package jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
}
