package jpa;

import jpa.domain.Distance;
import jpa.domain.Line;
import jpa.domain.Station;
import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
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

	@Autowired
	private StationRepository stationRepository;

	@Test
	void 노선_조회_시_속한_지하철역을_볼_수_있다() {
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("왕십리역", new Distance("상왕십리역", 750)));
		stations.add(new Station("강변역", new Distance("건대입구역", 1050)));

		Line line = new Line("연두색", "2호선", stations);

		lineRepository.save(line);

		Line findList = lineRepository.findByName("2호선");
		assertThat(findList.getStationList().contains(new Station("강변역", new Distance("건대입구역", 1050))));

		assertAll(
				() -> assertThat(findList.getStationList().contains(new Station("왕십리역", new Distance("상왕십리역", 750)))),
				() -> assertThat(findList.getStationList().contains(new Station("강변역", new Distance("건대입구역", 1050))))
		);
	}

	@Test
	void 노선에_역을_추가할_때는_이전_역과_얼마나_차이가_나는지_길이를_알고_있어야_한다() {
		List<Station> stations = new ArrayList<>();
		Station saveStation = new Station("왕십리역",new Distance("상왕십리역", 750));
		stationRepository.save(saveStation);
		stations.add(saveStation);

		Line line = new Line("연두색", "2호선", stations);


		lineRepository.save(line);

		Line findLine = lineRepository.findByName("2호선");

		Station findStation = findLine.getStationList().get(0);
		System.out.println(findStation.getDistance());

	}
}
