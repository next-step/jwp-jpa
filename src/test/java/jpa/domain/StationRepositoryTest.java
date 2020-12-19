package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class StationRepositoryTest {
	
	@Autowired
	private StationRepository stationRepository;

	@Test
	void saveTest() {
		Station expected = new Station("문래역");
		Station actual = stationRepository.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	void updateTest() {
		String originName = "문래역";
		String changeName = "잠실역";
		Station expected = new Station(originName);
		Station actual = stationRepository.save(expected);
		actual.changeName(changeName);
		assertAll(
			() -> assertThat(stationRepository.findByName(originName)).isNull(),
			() -> assertThat(stationRepository.findByName(changeName).getName()).isEqualTo(changeName)
		);
	}

	@Test
	void deleteTest() {
		String originName = "문래역";
		Station expected = new Station(originName);
		Station actual = stationRepository.save(expected);
		assertThat(actual.getName()).isEqualTo(expected.getName());

		stationRepository.delete(actual);
		assertAll(
			() -> assertThat(stationRepository.findByName(originName)).isNull(),
			() -> assertThat(stationRepository.findAll().size()).isEqualTo(0)
		);
	}

	@Test
	void findByNameTest() {
		String expected = "문래역";
		stationRepository.save(new Station(expected));
		String actual = stationRepository.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void insertUniqueTest() {
		String existName = "문래역";
		stationRepository.save(new Station(existName));
		assertThatThrownBy(() -> stationRepository.save(new Station(existName))).isInstanceOf(
			DataIntegrityViolationException.class);
	}

	@Test
	void updateUniqueTest() {
		String existName = "문래역";
		String newName = "잠실역";
		stationRepository.save(new Station(existName));
		Station newStation = stationRepository.save(new Station(newName));
		newStation.changeName(existName);

		assertThatThrownBy(() -> stationRepository.findByName(existName)).isInstanceOf(
			DataIntegrityViolationException.class);

		// save 시점에 exception이 될 줄 알았지만 쓰기 지연으로 인해 에러가 나지 않음 -> findByName을 할 때 flush 되며 발생
		// assertThatThrownBy(() -> stationRepository.save(newStation)).isInstanceOf(
		// 	DataIntegrityViolationException.class);
	}
}