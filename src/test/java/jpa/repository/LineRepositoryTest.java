package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {

	@Autowired
	LineRepository lines;

	@Autowired
	StationRepository stationRepository;

	@BeforeEach
	void setUp() {
		Line line = new Line("신분당선", "red");
		lines.save(line);
		Set<Station> stations = new HashSet<>();
		stations.add(new Station("강남역"));
		stations.add(new Station("판교역"));

		line.addAllStation(stations);
		this.stationRepository.saveAll(stations);
	}

	@DisplayName("라인 저장 테스트")
	@Test
	void save() {
		Line expected = new Line("신분당선", "red");
		Line actual = lines.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
		);
	}

	@DisplayName("라인 조회 테스트")
	@Test
	void findByName() {
		String expected = "신분당선";
		lines.save(new Line("신분당선", "red"));
		String actualName = lines.findByName(expected).getName();
		assertThat(actualName).isEqualTo(expected);
	}

	@DisplayName("같은 색 라인 조회 테스트")
	@Test
	void findByColor() {
		lines.save(new Line("신분당선", "red"));
		lines.save(new Line("5호선", "red"));

		List<Line> red = lines.findByColor("red");

		assertThat(red.size()).isEqualTo(2);
	}

	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있는 테스트")
	@Test
	void 노선조회_TEST() {
		Line 신분당선 = lines.findByName("신분당선");
		assertThat(신분당선.getStation().size()).isEqualTo(2);
	}
}
