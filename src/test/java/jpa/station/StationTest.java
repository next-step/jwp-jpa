package jpa.station;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.line.Line;
import jpa.line.LineRepository;

@DataJpaTest
class StationTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	private Station station;

	@BeforeEach
	void init() {
		Line line = lineRepository.save(new Line("3호선", Color.ORANGE));
		station = new Station("교대역");
		station.addLine(line);
		stationRepository.save(station);
	}

	@Test
	@DisplayName("JPA Auditing: 자동 시간 생성 엔티티 테스트")
	void createTimeEntityTest() {

		LocalDateTime now = LocalDateTime.now();

		Station expected = new Station("주안역"); // 영속상태 X
		assertThat(expected.getCreatedDate()).isNull();
		assertThat(expected.getModifiedDate()).isNull();

		Station actual = stationRepository.save(expected);
		assertThat(actual.getCreatedDate()).isNotNull();
		assertThat(actual.getModifiedDate()).isNotNull();

		assertThat(actual.getCreatedDate()).isAfter(now);
		assertThat(actual.getModifiedDate()).isAfter(now);
	}

	@Test
	@DisplayName("양방향 연결편의 메서드: Station Add -> Line 연결편의 메서드 추가 연결 테스트")
	void stationAddLineTest() {
		final Station expected = stationRepository.save(new Station("종로3가"));
		final Line lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		final Line lineNumber5 = lineRepository.save(new Line("5호선", Color.MAGENTA));

		expected.addLine(lineNumber1);
		expected.addLine(lineNumber5);
		final Station actual = stationRepository.findByName("종로3가");
		final Line actualLine1 = lineRepository.findByName("1호선");
		final Line actualLine5 = lineRepository.findByName("5호선");

		assertThat(actual).isNotNull();
		assertThat(actual.getLines()).hasSize(2);
		assertThat(actualLine1.getStations()).hasSize(1);
		assertThat(actualLine5.getStations()).hasSize(1);
	}
}
