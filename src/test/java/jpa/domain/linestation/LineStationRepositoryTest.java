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
		int expectedDistance = 20;
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
		assertThat(expectedDistance).isEqualTo(findLineStation.getDistance());
	}

	@Test
	public void updateLineStation() {
		// given
		String expectedStationName = "교대역";
		int expectedDistance = 30;
		Line line = new Line("green", "2호선");
		Station previousStation = new Station("서초역");
		Station station = new Station("강남역");

		lines.save(line);
		stations.save(station);
		stations.save(previousStation);
		lineStations.save(new LineStation(line, previousStation, null, 0));
		Long lineStationId = lineStations.save(new LineStation(line, station, previousStation, 50)).getId();

		// when
		Station newStation = new Station("교대역");
		stations.save(newStation);
		LineStation newLineStation = new LineStation(line, newStation, previousStation, 20);
		lineStations.save(newLineStation);

		LineStation lineStation = lineStations.findById(lineStationId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		lineStation.setPreviousStation(newStation);
		lineStation.setDistance(30);

		// then
		LineStation findLineStation = lineStations.findById(lineStationId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		assertThat(expectedStationName).isEqualTo(findLineStation.getPreviousStation().getName());
		assertThat(expectedDistance).isEqualTo(findLineStation.getDistance());
	}
}