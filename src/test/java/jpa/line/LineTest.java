package jpa.line;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.position.Position;
import jpa.position.PositionRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

@DataJpaTest
class LineTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private PositionRepository positionRepository;

	private Station 시청;

	@BeforeEach
	void init() {
		시청 = stationRepository.save(new Station("시청"));
		stationRepository.flush();
	}

	@Test
	@DisplayName("노선 라인 생성 테스트")
	void initLineTest() {
		Line actual = lineRepository.save(new Line("2호선", Color.GREEN));

		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo("2호선"),
			() -> assertThat(actual.getColor()).isEqualTo(Color.GREEN)
		);
	}

	@Test
	@DisplayName("라인 전철 및 거리 추가 테스트")
	void getStationsNameTest() {
		Line lineNumber2 = lineRepository.save(new Line("2호선", Color.GREEN));

		Position 시청Position = positionRepository.save(new Position(시청, lineNumber2.getId(), 10L));
		lineNumber2.addStation(시청, 시청Position);

		Station actual = stationRepository.findByName("시청");
		assertThat(actual.getPosition(lineNumber2.getId())).isEqualTo(10L);
	}
}
