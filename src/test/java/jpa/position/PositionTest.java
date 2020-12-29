package jpa.position;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

@DataJpaTest
class PositionTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private PositionRepository positionRepository;

	private Station 시청;

	private Line lineNumber2;

	@BeforeEach
	void init() {
		시청 = stationRepository.save(new Station("시청"));
		lineNumber2 = lineRepository.save(new Line("2호선", Color.GREEN));
		stationRepository.flush();
	}

	@Test
	@DisplayName("포지션 생성 테스트")
	void initPositionTest() {

		System.out.println(lineNumber2.getId());
		Position actual = positionRepository.save(new Position(시청, lineNumber2.getId(), 10L));

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getDistance()).isEqualTo(10L),
			() -> assertThat(actual.getStation()).isEqualTo(시청)
		);
	}
}


