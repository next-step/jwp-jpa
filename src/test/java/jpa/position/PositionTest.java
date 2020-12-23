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
	private Line lineNumber3;
	private Line lineNumber5;

	@BeforeEach
	void init() {
		시청 = stationRepository.save(new Station("시청"));
		서울 = stationRepository.save(new Station("서울"));
		용산 = stationRepository.save(new Station("용산"));

		lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		lineNumber2 = lineRepository.save(new Line("2호선", Color.GREEN));
		lineNumber3 = lineRepository.save(new Line("3호선", Color.ORANGE));
		lineNumber5 = lineRepository.save(new Line("5호선", Color.MAGENTA));

		lineNumber1.addStation(시청);
		lineNumber1.addStation(서울);
		lineNumber1.addStation(용산);

		lineNumber2.addStation(시청);
		stationRepository.flush();
	}

	@Test
	@DisplayName("위치 엔티티 생성 테스트")
	void createPositionTest() {

		Position pLine2시청 = positionRepository.save(new Position(78));
		Position pLine1시청 = positionRepository.save(new Position(20));

		Position pLine1서울 = positionRepository.save(new Position(12));
		Position pLine1용산 = positionRepository.save(new Position(5));

		assertThat(positionRepository.findAll()).hasSize(4);
		System.out.println(positionRepository.findAll());
	}

	@Test
	@DisplayName("위치 엔티티 생성 테스트")
	void joinPositionTest() {

		Position pLine1시청 = positionRepository.save(new Position(20));
		Position pLine2시청 = positionRepository.save(new Position(78));

		Position pLine1서울 = positionRepository.save(new Position(12));
		Position pLine1용산 = positionRepository.save(new Position(5));

		assertThat(positionRepository.findAll()).hasSize(4);
		System.out.println(positionRepository.findAll());
	}

}
