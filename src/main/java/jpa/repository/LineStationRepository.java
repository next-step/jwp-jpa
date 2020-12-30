package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.LineStation;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
}
