package jpa.repository;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Location;
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
		Station station2 = new Station("양재역");
		Station station3 = new Station("판교역");
		Station station4 = new Station("서초역");
		entityManager.persist(station1);
		entityManager.persist(station2);
		entityManager.persist(station3);
		entityManager.persist(station4);

		LineStation line_station1 = new LineStation(line, station1);
		LineStation line_station2 = new LineStation(line, station2, new Location(7, station1));
		LineStation line_station3 = new LineStation(line, station3, new Location(10, station2));
		LineStation line2_station1 = new LineStation(line2, station1);
		LineStation line2_station4 = new LineStation(line2, station4, new Location(10, station1));
		entityManager.persist(line_station1);
		entityManager.persist(line_station2);
		entityManager.persist(line_station3);
		entityManager.persist(line2_station1);
		entityManager.persist(line2_station4);
	}

	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있는 테스트")
	@Test
	void 노선으로_역_이름을_가져오는_TEST() {
		assertThat(lineRepository.findByName("신분당선").isEqualsContainsStationSize(3)).isTrue();
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

	@DisplayName("이전 역과 얼차이나는지 거리를 가져온다.")
	@Test
	void 거리_TEST() {
		LineStation 신분당선_양재역 = lineStationRepository.findByLineNameAndStationName("신분당선", "양재역");
		LineStation 이호선_서초역 = lineStationRepository.findByLineNameAndStationName("2호선", "서초역");

		assertThat(신분당선_양재역.getDistance()).isEqualTo(7);
		assertThat(신분당선_양재역.getPreviousStation().getName()).isEqualTo("강남역");

		assertThat(이호선_서초역.getDistance()).isEqualTo(10);
		assertThat(이호선_서초역.getPreviousStation().getName()).isEqualTo("강남역");
	}
}
