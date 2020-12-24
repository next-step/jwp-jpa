package jpa.line;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
	private Station 서울;
	private Station 용산;

	private Line lineNumber1;

	@BeforeEach
	void init() {
		시청 = stationRepository.save(new Station("시청"));
		서울 = stationRepository.save(new Station("서울"));
		용산 = stationRepository.save(new Station("용산"));

		lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));

		lineNumber1.addStation(시청);
		lineNumber1.addStation(서울);
		lineNumber1.addStation(용산);

		stationRepository.flush();
	}

	@Test
	@DisplayName("노선 조회 시, 속한 지하철역을 볼 수 있다.")
	void getStationsNameTest() {
		Line line1 = lineRepository.findByName("1호선");

		assertThat(line1.getStationsName()).hasSize(3);
		System.out.println(line1.getStationsName());
	}
}
