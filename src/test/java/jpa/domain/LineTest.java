package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class LineTest {

	@Autowired
	private EntityManager em;

	private Line 이호선;
	private Station 삼성역;
	private Station 잠실역;

	@BeforeEach
	void setUp() {
		이호선 = new Line("2호선", "green");
		삼성역 = new Station("삼성역");
		잠실역 = new Station("잠실역");
		em.persist(이호선);
		em.persist(삼성역);
		em.persist(잠실역);
		em.flush();
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void addStation(boolean clear) {
		// when
		이호선.addStation(삼성역);
		em.flush();
		if (clear) em.clear();

		// then
		Line line1 = em.find(Line.class, 이호선.getId());
		assertThat(line1.getLineStations()).hasSize(1)
				.first()
				.extracting(LineStation::getStation).isEqualTo(삼성역)
				.extracting(Station::getName).isEqualTo("삼성역");
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void getStations(boolean clear) {
		// given
		이호선.addStation(삼성역);
		이호선.addStation(잠실역);
		em.flush();
		if (clear) em.clear();

		// when
		assertThat(em.find(Line.class, 이호선.getId()))
				.extracting(Line::getStations)
				.asList()
				.hasSize(2)
				.containsExactly(삼성역, 잠실역);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	@DisplayName("이호선 이 remove 됐을때 lineStation 또한 remove 되고, Station 은 잘 살아 있는지 확인")
	void cascade_remove(boolean clear) {
		// given
		이호선.addStation(삼성역);
		em.flush();
		LineStation lineStation = 이호선.getLineStations().get(0);

		// when
		em.remove(이호선);
		em.flush();
		if (clear) em.clear();

		// then
		assertThat(em.find(LineStation.class, lineStation.getId())).isNull();
		assertThat(em.find(Station.class, 삼성역.getId())).isNotNull();
	}
}
