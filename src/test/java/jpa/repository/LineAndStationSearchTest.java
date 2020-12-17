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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineAndStationSearchTest {

	@Autowired
	LineRepository lines;

	@Autowired
	StationRepository stationRepository;

	@Autowired
	EntityManager entityManager;

	@BeforeEach
	void setUp() {
		Line line = new Line("신분당선", "red");
		Line line2 = new Line("2호선", "green");
		entityManager.persist(line);
		entityManager.persist(line2);

		Station 강남역 = new Station("강남역");
		Station 판교역 = new Station("판교역");
		entityManager.persist(강남역);
		entityManager.persist(판교역);

		entityManager.persist(new LineStation(line, 강남역));
		entityManager.persist(new LineStation(line2, 강남역));
		entityManager.persist(new LineStation(line, 판교역));

		entityManager.flush();
		entityManager.clear();
	}

	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있는 테스트")
	@Test
	void 노선으로_역_이름을_가져오는_TEST() {
		assertThat(lines.findByName("신분당선").getLineStations().size()).isEqualTo(2);
		assertThat(lines.findByName("2호선").getLineStations().size()).isEqualTo(1);
		assertThat(stationRepository.findByName("강남역").getLineStation().size()).isEqualTo(2);
	}

	@DisplayName("역을 삭제하면 라인과의 관계가 끊어지는지 테스트")
	@Test
	void 삭제_테스트() {
		stationRepository.deleteByNameEquals("강남역");
		entityManager.flush();

		assertThat(lines.findByName("신분당선").getLineStations().size()).isEqualTo(1);
		assertThat(lines.findByName("2호선").getLineStations().size()).isZero();
	}
}
