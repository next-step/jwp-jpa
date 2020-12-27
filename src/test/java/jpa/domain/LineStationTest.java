package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineStationTest {

	@Autowired
	private EntityManager em;

	private LineStation lineStation;

	@BeforeEach
	void setUp() {
		Line line = new Line("2호선", Color.GREEN);
		Station station = new Station("삼성역");
		lineStation = new LineStation(line, station, Distance.of(40));
		em.persist(line);
		em.persist(station);
		em.persist(lineStation);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void changePrevStationDistance(boolean clear) {
		// when
		lineStation.changePrevStationDistance(Distance.of(999));
		em.flush();
		if (clear) em.clear();

		// then
		assertThat(lineStation.getPrevStationDistance()).isEqualTo(Distance.of(999));
	}
}
