package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineStationRepositoryTest {
	@Autowired
	private StationRepository stations;
	@Autowired
	private LineRepository lines;

	@Test
	void create_test() {
		Station station = stations.save(new Station("온수역"));
		Station previousStation = stations.save(new Station("오류역"));
		Line line = lines.save(new Line("1호선", "남색"));

		line.addStation(station, previousStation, 10);

		Station actual = stations.findByName("온수역").orElse(new Station());
		assertThat(actual.getLines())
			.hasSize(1)
			.contains(line);
	}

	@Test
	void remove_test(){
		Station station = stations.findByName("여의도역").orElse(new Station());
		stations.delete(station);
		stations.flush();

		Line line = lines.findByName("9호선").orElse(new Line());

		assertThat(line.getStations()).doesNotContain(station);
	}
}