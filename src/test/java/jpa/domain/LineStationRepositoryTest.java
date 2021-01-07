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
	void test() {
		Station station = stations.save(new Station("온수역"));
		Line line = lines.save(new Line("1호선", "남색"));

		line.addStation(station);

		Station actual = stations.findByName("온수역").orElse(new Station());
		assertThat(actual.getLines())
			.hasSize(1)
			.contains(line);
	}
}