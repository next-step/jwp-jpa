package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Station;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@DataJpaTest
public class StationRepositoryTest {
	@Autowired
	private StationRepository stations;

	@Test
	void save() {
		Station expected = new Station("잠실역");
		Station actual = stations.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	void findByName() {
		String expected = "잠실역";
		stations.save(new Station(expected));
		String actual = stations.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}
}
