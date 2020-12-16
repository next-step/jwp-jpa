package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineAndStationSearchTest {

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

	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있는 테스트")
	@Test
	void 노선으로_역_이름을_가져오는_TEST() {
		Line 신분당선 = lines.findByName("신분당선");
		assertThat(신분당선.getStation().size()).isEqualTo(2);
	}

	@DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있는 테스트")
	@Test
	void 역으로_노선_이름을_가져오는_TEST() {
		Station 강남역 = stationRepository.findByName("강남역");
		assertThat(강남역.getLineName()).isEqualTo("신분당선");
	}
}
