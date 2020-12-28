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
		Station prevStation = new Station("선릉역");
		ConnectedStation prevConnected = new ConnectedStation(40, prevStation);
		lineStation = new LineStation(line, station, prevConnected);
		em.persist(line);
		em.persist(station);
		em.persist(prevStation);
		em.persist(lineStation);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void changePrevStationDistance(boolean clear) {
		// when
		Station prevStation = new Station("종합운동장역");
		ConnectedStation prevConnected2 = new ConnectedStation(50, prevStation);
		em.persist(prevStation);
		lineStation.changePrevStationDistance(prevConnected2);
		em.flush();
		if (clear) em.clear();

		// then
		assertThat(lineStation.getPrevConnectedStation()).isEqualTo(prevConnected2);
	}
}
