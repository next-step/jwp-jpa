package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.LineStation;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
}
