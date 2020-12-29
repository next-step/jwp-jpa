package jpa.domain.line;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;

@DataJpaTest
class LineRepositoryTest {
	@Autowired
	EntityManager em;

	@Autowired
	private LineRepository lines;

	@Autowired
	private StationRepository stations;

	@DisplayName("Line save 테스트")
	@Test
	public void save() {
		// given
		Line expected = new Line("green", "2호선");

		// when
		Line actual = lines.save(expected);

		// then
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@DisplayName("Line findByColor 테스트")
	@Test
	public void findByColor() {
		// given
		String expected = "green";

		// when
		lines.save(new Line("green", "2호선"));

		// then
		String actual = lines.findByColor(expected).get(0).getColor();
		assertThat(expected).isEqualTo(actual);
	}

	@DisplayName("Line findByName 테스트")
	@Test
	public void findByName() {
		// given
		String expected = "2호선";

		// when
		lines.save(new Line("green", "2호선"));

		// then
		String actual = lines.findByName(expected).getName();
		assertThat(expected).isEqualTo(actual);
	}

	@DisplayName("Line update 테스트")
	@Test
	public void update() {
		// given
		Line newLine = new Line("green", "2호선");
		Long lineId = lines.save(newLine).getId();
		String expected = "5호선";

		// when
		newLine.setName(expected);

		// then
		Line findLine = lines
			.findById(lineId).orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		assertThat(expected).isEqualTo(findLine.getName());
	}

	@DisplayName("Line delete 테스트")
	@Test
	public void delete() {
		// given
		Line newLine = new Line("green", "2호선");
		Long lineId = lines.save(newLine).getId();

		// when
		lines.delete(newLine);

		// then
		assertThat(lines.findById(lineId).isPresent()).isFalse();
	}

	@Test
	public void saveWithStation() {
		// given
		int expected = 2;
		Station stationOne = new Station("강남역");
		Station stationTwo = new Station("잠실역");

		List<Station> stationList = new ArrayList<>();
		stationList.add(stationOne);
		stationList.add(stationTwo);

		Line line = new Line("green", "2호선", stationList);

		// when
		stations.save(stationOne);
		stations.save(stationTwo);
		Long lineId = lines.save(line).getId();

		em.flush();
		em.clear();

		// then
		Line findLine = lines
			.findById(lineId).orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		List<Station> findStation = findLine.getStations();
		for (Station s : findStation) {
			System.out.println("station name = " + s.getName());
		}
		assertThat(expected).isEqualTo(findStation.size());
	}
}
