package jpa.position;

import static org.assertj.core.api.Assertions.*;

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
	private Station 서울;
	private Station 용산;

	private Line lineNumber1;
	private Line lineNumber2;

	@BeforeEach
	void init() {
		시청 = stationRepository.save(new Station("시청"));
		서울 = stationRepository.save(new Station("서울"));
		용산 = stationRepository.save(new Station("용산"));

		lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		lineNumber2 = lineRepository.save(new Line("2호선", Color.GREEN));

		lineNumber1.addStation(시청);
		lineNumber1.addStation(서울);
		lineNumber1.addStation(용산);

		lineNumber2.addStation(시청);
		stationRepository.flush();
	}

	@Test
	@DisplayName("위치 엔티티 생성 테스트")
	void joinPositionTest() {

		Position pLine1시청 = positionRepository.save(new Position(20));
		Position pLine2시청 = positionRepository.save(new Position(78));
	}

	@Test
	@DisplayName("양방향: 위치 엔티티 양방향 거리 추가 테스트")
	void addLocationTest() {

		Position pLine1시청 = positionRepository.save(new Position(20));
		Position pLine2시청 = positionRepository.save(new Position(78));

		pLine1시청.addSubway(시청, lineNumber1);
		pLine2시청.addSubway(시청, lineNumber2);

		assertThat(positionRepository.findAll()).hasSize(2);
		assertThat(positionRepository.findByLine(lineNumber1)).hasSize(1);
		assertThat(positionRepository.findByStation(시청)).hasSize(2);

		assertThat(lineNumber1.getPositions()).hasSize(1);
		assertThat(lineNumber2.getPositions()).hasSize(1);
	}

	@Test
	@DisplayName("양방향: Station 엔티티로 위치값 가져오기 테스트")
	void getLocationToStationTest() {
		Position pLine1시청 = positionRepository.save(new Position(20));
		Position pLine2시청 = positionRepository.save(new Position(78));

		pLine1시청.addSubway(시청, lineNumber1);
		pLine2시청.addSubway(시청, lineNumber2);

		Station actual = stationRepository.findByName("시청");
		//assertThat(positionRepository.findByStation(actual)).hasSize(2);

		assertThat(actual.getPositions()).hasSize(2);
		assertThat(actual.getLocation(lineNumber1)).isEqualTo(20);
		assertThat(actual.getLocation(lineNumber2)).isEqualTo(78);
	}

	@Test
	@DisplayName("양방향: Line 엔티티로 위치값 가져오기 테스트")
	void getLocationToLintest() {
		Position pLine1용산 = positionRepository.save(new Position(50));
		Position pLine1서울 = positionRepository.save(new Position(57));
		Position pLine1시청 = positionRepository.save(new Position(65));

		pLine1용산.addSubway(용산, lineNumber1);
		pLine1서울.addSubway(서울, lineNumber1);
		pLine1시청.addSubway(시청, lineNumber1);

		Line actual = lineRepository.findByName("1호선");

		assertThat(positionRepository.findByLine(actual)).hasSize(3);
		assertThat(actual.getPositions()).hasSize(3);

		assertThat(actual.getLocation(용산)).isEqualTo(50);
		assertThat(actual.getLocation(서울)).isEqualTo(57);
		assertThat(actual.getLocation(시청)).isEqualTo(65);
	}
}


