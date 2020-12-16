package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.LineStation;
import jpa.domain.entity.LineStationId;

public interface LineStationRepository extends JpaRepository<LineStation, LineStationId> {
}
