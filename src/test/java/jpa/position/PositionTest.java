package jpa.position;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

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

	@Test
	@DisplayName("포지션 생성 테스트")
	void initPositionTest() {
		// given
		Station 강남 = 전철_역_생성("강남");
		Station 서초 = 전철_역_생성("서초");
		Line line2 = 전철_노선_생성("2호선", Color.GREEN);

		// when
		Position expected = new Position(line2, 강남, 서초, 10L);
		Position actual = positionRepository.save(expected);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getLine()).isNotNull(),
			() -> assertThat(actual.getUpStation().getName()).isEqualTo("강남"),
			() -> assertThat(actual.getDownStation().getName()).isEqualTo("서초"),
			() -> assertThat(actual.getDistance()).isEqualTo(10L)
		);
	}

	private Station 전철_역_생성(String name) {
		return stationRepository.save(new Station(name));
	}

	private Line 전철_노선_생성(String name, Color color) {
		return lineRepository.save(new Line(name, color));
	}
}


