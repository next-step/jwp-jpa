package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.dao.StationRepository;

@DataJpaTest
class StationTest {

	@Autowired
	private StationRepository stationRepository;

	@Test
	void createTableTest() {

		Station station = new Station("시청역");
		Station actual = stationRepository.save(station);

		station.changeName("사당역");
		Station actual2 = stationRepository.save(station);

		assertThat(actual).isNotNull();
		assertThat(actual).isEqualTo(actual2);
	}
}
