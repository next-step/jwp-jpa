package jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.entity.Line;
import jpa.entity.LineStation;
import jpa.entity.LineStationPk;

public interface LineStationRepository extends JpaRepository<LineStation, LineStationPk> {
	Optional<LineStation> findByLine(Line line);
}