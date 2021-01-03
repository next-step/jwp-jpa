package jpa.repository;

import jpa.domain.LineStation;
import jpa.domain.LineStationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, LineStationId> {

}
