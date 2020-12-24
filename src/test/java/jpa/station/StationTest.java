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
	@DisplayName("양방향 연관 관계: 연관관계 편의 메서드 생성")
	void functionEntityTest() {
		final Line lineNumber3 = lineRepository.findByName("3호선");
		final Station expected = new Station("양재역");
		// 1대 다 단방향, 쿼리 생성 확인
		assertThat(lineNumber3.getStations()).hasSize(1);

		stationRepository.save(expected);
		lineNumber3.addStation(expected);  // 연관관계 편의 메서드
		stationRepository.flush();

		assertThat(lineNumber3.getStations()).hasSize(2);
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

	@Test
	@DisplayName("양방향 연결편의 메서드: Line Add -> Station 연결편의 메서드 추가 연결 테스트")
	void lineAddStationTest() {
		final Station expected = stationRepository.save(new Station("종로3가"));
		final Line lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		final Line lineNumber5 = lineRepository.save(new Line("5호선", Color.MAGENTA));

		lineNumber1.addStation(expected);
		lineNumber5.addStation(expected);

		final Station actual = stationRepository.findByName("종로3가");
		final Line actualLine1 = lineRepository.findByName("1호선");
		final Line actualLine5 = lineRepository.findByName("5호선");

		assertThat(actual).isNotNull();
		assertThat(actual.getLines()).hasSize(2);
		assertThat(actualLine1.getStations()).hasSize(1);
		assertThat(actualLine5.getStations()).hasSize(1);
	}

	@Test
	@DisplayName("양방향 연결편의 메서드: 연결편의 메서드 추가 연결 테스트")
	void stationAndLineTest() {
		final Line newLine = lineRepository.save(new Line("2호선", Color.GREEN));
		station.addLine(newLine);

		assertThat(station.getLines()).hasSize(2); // 2호선, 3호선
		assertThat(newLine.getStations()).hasSize(1); // 교대역

		final Station newStation = stationRepository.save(new Station("강남역"));
		newLine.addStation(newStation);

		assertThat(newLine.getStations()).hasSize(2); // 교대역, 강남역
	}

	@Test
	@DisplayName("양방향 연결편의 메서드: Station clear -> Line 테스트")
	void stationClearLineTest() {
		final Station expected = stationRepository.save(new Station("종로3가"));
		final Line lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		final Line lineNumber5 = lineRepository.save(new Line("5호선", Color.MAGENTA));

		lineNumber1.addStation(expected);
		lineNumber5.addStation(expected);
		stationRepository.flush();

		expected.clearLine(lineNumber1);

		final Station actual = stationRepository.findByName("종로3가");
		final Line actualLine1 = lineRepository.findByName("1호선");
		final Line actualLine5 = lineRepository.findByName("5호선");

		assertThat(actual.getLines()).hasSize(1);
		assertThat(actualLine1.getStations()).isEmpty();
		assertThat(actualLine5.getStations()).hasSize(1);
	}

	@Test
	@DisplayName("양방향 연결편의 메서드: Line clear -> Station 테스트")
	void lineClearStationTest() {
		final Station expected = stationRepository.save(new Station("종로3가"));
		final Line lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		final Line lineNumber5 = lineRepository.save(new Line("5호선", Color.MAGENTA));

		lineNumber1.addStation(expected);
		lineNumber5.addStation(expected);
		stationRepository.flush();

		lineNumber1.clearStation(expected);

		final Station actual = stationRepository.findByName("종로3가");
		final Line actualLine1 = lineRepository.findByName("1호선");
		final Line actualLine5 = lineRepository.findByName("5호선");

		assertThat(actual.getLines()).hasSize(1);
		assertThat(actualLine1.getStations()).isEmpty();
		assertThat(actualLine5.getStations()).hasSize(1);
	}

	@Test
	@DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
	void getLinesNameTest() {
		final Station expected = stationRepository.save(new Station("종로3가"));
		final Line lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		final Line lineNumber5 = lineRepository.save(new Line("5호선", Color.MAGENTA));

		lineNumber1.addStation(expected);
		lineNumber5.addStation(expected);
		stationRepository.flush();

		Station actual = stationRepository.findByName("종로3가");

		assertThat(actual.getLines()).hasSize(2);
		System.out.println(actual.getLinesName());
	}
}
