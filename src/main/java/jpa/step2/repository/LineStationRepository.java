package jpa.step2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.step2.domain.LineStation;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
}
