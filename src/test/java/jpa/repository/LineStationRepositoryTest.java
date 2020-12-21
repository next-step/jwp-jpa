package jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.LineStation;
import jpa.domain.Station;

/**
 * @author : byungkyu
 * @date : 2020/12/21
 * @description :
 **/
@DataJpaTest
public class LineStationRepositoryTest {

	@Autowired
	private LineStationRepository lineStations;

	@Autowired
	private StationRepository stations;

	@Test
	void saveWithLineStation() {
		LineStation lineStation = lineStations.save(new LineStation());
		stations.save(new Station("잠실역", lineStation));
	}
}
