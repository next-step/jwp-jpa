package jpa.repository;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineAndStationSearchTest {

	@Autowired
	LineRepository lineRepository;

	@Autowired
	StationRepository stationRepository;

	@Autowired
	LineStationRepository lineStationRepository;

	@Autowired
	EntityManager entityManager;

	@BeforeEach
	void setUp() {
		Line line = new Line("신분당선", "red");
		Line line2 = new Line("2호선", "green");
		entityManager.persist(line);
		entityManager.persist(line2);

		Station station1 = new Station("강남역");
		Station station2 = new Station("판교역");
		entityManager.persist(station1);
		entityManager.persist(station2);

		entityManager.persist(new LineStation(line, station1));
		entityManager.persist(new LineStation(line2, station1));
		entityManager.persist(new LineStation(line, station2));
	}

	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있는 테스트")
	@Test
	void 노선으로_역_이름을_가져오는_TEST() {
		assertThat(lineRepository.findByName("신분당선").isEqualsContainsStationSize(2)).isTrue();
		assertThat(lineRepository.findByName("2호선").isEqualsContainsStationSize(1)).isTrue();
		assertThat(stationRepository.findByName("강남역").isEqualsContainsLineSize(2)).isTrue();
	}

	@DisplayName("역을 삭제하면 연결 엔티티 데이터가 삭제되는지 테스트")
	@Test
	void 역_삭제_테스트() {
		Station findStation = stationRepository.findByName("강남역");
		entityManager.remove(findStation);
		List<LineStation> byStationEquals = lineStationRepository.findByStationEquals(findStation);

		assertThat(byStationEquals).isEmpty();
		assertThat(stationRepository.findByName("강남역")).isNull();
	}

	@DisplayName("라인을 삭제하면 연결 엔티티 데이터가 삭제되는지 테스트")
	@Test
	void 라인_삭제_테스트() {
		Line findLine = lineRepository.findByName("2호선");
		entityManager.remove(findLine);
		List<LineStation> byLineEquals = lineStationRepository.findByLineEquals(findLine);

		assertThat(byLineEquals).isEmpty();
		assertThat(lineRepository.findByName("2호선")).isNull();
	}
}
