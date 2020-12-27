package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class StationTest {

	@Autowired
	private EntityManager em;

	private Line 이호선;
	private Line 신분당선;
	private Station 선릉역;

	@BeforeEach
	void setUp() {
		이호선 = new Line("이호선", Color.GREEN);
		신분당선 = new Line("신분당선", Color.YELLOW);
		선릉역 = new Station("선릉역");
		em.persist(이호선);
		em.persist(신분당선);
		em.persist(선릉역);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void getLines(boolean clean) {
		// given
		이호선.addStation(선릉역, Distance.of(50));
		신분당선.addStation(선릉역, Distance.of(40));
		em.flush();
		if (clean) em.clear();

		// when & then
		assertThat(선릉역.getLines()).hasSize(2)
				.containsExactly(이호선, 신분당선);
	}
}
