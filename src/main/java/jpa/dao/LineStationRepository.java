package jpa.dao;

import jpa.domain.LineStation;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {

}
