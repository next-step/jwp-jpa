package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LineRepositoryTest {
	@Autowired
	private StationRepository stations;
	@Autowired
	private LineRepository lines;

	@Test
	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있다.")
	void getLine_test() {
		Line lineTow = lines.findByName("2호선").orElse(new Line());
		Station expectedStation1 = stations.findByName("당산역").orElse(new Station());
		Station expectedStation2 = stations.findByName("홍대입구역").orElse(new Station());

		assertThat(lineTow.getStations())
			.hasSize(2)
			.contains(expectedStation1, expectedStation2);
	}
}
