package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.position.Position;
import jpa.position.PositionRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

@DataJpaTest
class SubwayStationTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Test
	@DisplayName("지하철 역 하나, 자동 생성 테스트")
	void createSubwayTest() {

		Station station = stationRepository.save(new Station("주안역"));
		Line line = lineRepository.save(new Line("1호선", Color.BLUE));
		Position position = positionRepository.save(new Position(10));

		Subway.createSubway(station, line, position);
		Station actual = stationRepository.findByName("주안역");

		assertThat(actual.getPositions()).hasSize(1);
		assertThat(actual.getLines()).hasSize(1);
		assertThat(actual.getLocation(line)).isEqualTo(10);
	}
}
