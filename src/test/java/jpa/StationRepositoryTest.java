package jpa;

import jpa.domain.Distance;
import jpa.domain.Line;
import jpa.domain.Station;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stations;

	@Test
	void save() {
		Station expected = new Station("잠실역", new Distance("잠실새내", 1250));
		Station actual = stations.save(expected);
		assertAll(
				() -> assertThat(actual.getId()).isNotNull(),
				() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	void findByName() {
		String expected = "잠실역";
		stations.save(new Station(expected, new Distance("잠실새내", 1250)));
		String actual = stations.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void 지하철역은_여러_노선에_소속될_수_있다(){
		List<Line> lineList = new ArrayList<>();
		lineList.add(new Line("연두색", "2호선"));
		lineList.add(new Line("보라색", "5호선"));

		stations.save(new Station("왕십리역", lineList, new Distance("상왕십리", 1250)));

		assertThat(stations.findByName("왕십리역").getLineList().size()).isEqualTo(2);

	}

	@Test
	void 지하철역_조회_시_어느_노선에_속한지_볼_수_있다(){
		List<Line> lineList = new ArrayList<>();
		lineList.add(new Line("연두색", "2호선"));
		lineList.add(new Line("보라색", "5호선"));

		stations.save(new Station("왕십리역", lineList, new Distance("상왕십리역", 1250)));
		Station findStation = stations.findByName("왕십리역");
		assertAll(
				() -> assertThat(findStation.getLineList().contains(new Line("연두색", "2호선"))),
				() -> assertThat(findStation.getLineList().contains(new Line("보라색", "5호선")))
		);
	}

}
