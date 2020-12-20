package jpa;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.repository.LineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class LineRepositoryTest {

	@Autowired
	private LineRepository lineRepository;

	@Test
	void 노선_조회_시_속한_지하철역을_볼_수_있다(){
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("왕십리역"));
		stations.add(new Station("강변역"));

		Line line = new Line("연두색","2호선",stations);

		lineRepository.save(line);

		Line findList = lineRepository.findByName("2호선");
		assertThat(findList.getStationList().contains(new Station("강변역")));

		assertAll(
				() -> assertThat(findList.getStationList().contains(new Station("왕십리역"))),
				() -> assertThat(findList.getStationList().contains(new Station("강변역")))
		);
	}
}
