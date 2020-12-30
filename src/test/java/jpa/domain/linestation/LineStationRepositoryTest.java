package jpa.domain.linestation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;

@DataJpaTest
class LineStationRepositoryTest {
	@Autowired
	LineStationRepository lineStations;

	@Autowired
	LineRepository lines;

	@Autowired
	StationRepository stations;

	@Test
	public void findLineStationWithLineAndStation() {
		// given
		String expectedPreviousStationName = "서초역";
		String expectedStationName = "강남역";
		String expectedLineColor = "green";
		String expectedLineName = "2호선";
		Line line = new Line(expectedLineColor, expectedLineName);
		Station previousStation = new Station(expectedPreviousStationName);
		Station station = new Station(expectedStationName);

		// when
		lines.save(line);
		stations.save(station);
		stations.save(previousStation);

		LineStation previousLineStation = new LineStation(line, previousStation, null, 0);
		LineStation lineStation = new LineStation(line, station, previousStation, 20);
		Long previousLineStationId = lineStations.save(previousLineStation).getId();
		Long lineStationId = lineStations.save(lineStation).getId();

		// then
		LineStation findPreviousLineStation = lineStations.findById(previousLineStationId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		LineStation findLineStation = lineStations.findById(lineStationId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		assertThat(expectedPreviousStationName).isEqualTo(findPreviousLineStation.getStation().getName());
		assertThat(expectedStationName).isEqualTo(findLineStation.getStation().getName());
		assertThat(expectedLineColor).isEqualTo(findPreviousLineStation.getLine().getColor());
		assertThat(expectedLineName).isEqualTo(findPreviousLineStation.getLine().getName());
		assertThat(expectedLineColor).isEqualTo(findLineStation.getLine().getColor());
		assertThat(expectedLineName).isEqualTo(findLineStation.getLine().getName());
	}

}