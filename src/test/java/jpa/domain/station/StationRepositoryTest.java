package jpa.domain.station;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.line.LineRepository;

@DataJpaTest
class StationRepositoryTest {
	@Autowired
	StationRepository stations;

	@DisplayName("Station save 테스트")
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

	@DisplayName("Station findByName 테스트")
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

	@DisplayName("Station update 테스트")
	@Test
	public void update() {
		// given
		Station newStation = new Station("잠실역");
		Long stationId = stations.save(newStation).getId();
		String expected = "강남역";

		// when
		newStation.setName(expected);

		// then
		Station findStation = stations.findById(stationId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		assertThat(expected).isEqualTo(findStation.getName());
	}

	@DisplayName("Station delete 테스트")
	@Test
	public void delete() {
		// given
		Station newStation = new Station("잠실역");
		Long stationId = stations.save(newStation).getId();

		// when
		stations.delete(newStation);

		// then
		assertThat(stations.findById(stationId).isPresent()).isFalse();
	}
}
