package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;

@DataJpaTest
class StationTest {
	@Autowired
	StationRepository stations;

	@Test
	public void save() {
		// given
		Station expected = new Station("잠실역");

		// when
		Station actual = stations.save(expected);

		// then
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	public void findByName() {
		// given
		String expected = "잠실역";

		// when
		stations.save(new Station("잠실역"));

		// then
		String actual = stations.findByName(expected).getName();
		assertThat(expected).isEqualTo(actual);
	}
}