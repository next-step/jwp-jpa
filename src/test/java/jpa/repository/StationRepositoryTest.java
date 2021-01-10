package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Station;

@DataJpaTest
public class StationRepositoryTest {

	@Autowired
	private StationRepository stationRepository;

	@DisplayName("Station 등록")
	@Test
	void given_station_when_save_then_return_created_station_with_primary_key() {
		final String stationName = "사당역";
		Station station = new Station(stationName);

		Station createdStation = stationRepository.save(station);

		assertAll(
			() -> assertThat(createdStation.getId()).isNotNull(),
			() -> assertThat(createdStation.getName()).isEqualTo(station.getName())
		);
	}

	@DisplayName("Station 조회")
	@Test
	void given_station_when_save_and_findByName_then_return_created_station() {
		final String stationName = "사당역";
		Station station = new Station(stationName);
		stationRepository.save(station);

		Station actual = stationRepository.findByName(stationName)
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(actual.getName()).isEqualTo(station.getName()),
			() -> assertThat(actual == station).isTrue()
		);
	}

	@DisplayName("Station 등록시 유니크 키 제약조건 위반시 익셉션")
	@Test
	void given_station_duplicated_name_when_save_then_throw_exception() {
		final String stationName = "사당역";
		Station station = new Station(stationName);
		Station duplicationStation = new Station(stationName);
		stationRepository.save(station);

		assertThatThrownBy(() -> stationRepository.save(duplicationStation));
	}

}
