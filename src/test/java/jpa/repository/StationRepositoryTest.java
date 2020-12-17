package jpa.repository;

import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stations;

	@DisplayName("역 저장 테스트")
	@Test
	void save() {
		Station expected = new Station("잠실역");
		Station actual = stations.save(expected);
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@DisplayName("역 조회 테스트")
	@Test
	void findByName() {
		String expected = "잠실역";
		stations.save(new Station(expected));
		String actual = stations.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}
}
