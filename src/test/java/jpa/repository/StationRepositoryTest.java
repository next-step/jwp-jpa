package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.entity.Station;

@DisplayName("StationRepositoryTest : 가장 기본적인 JPA CRUD 테스트")
@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stationRepository;

	/**
	 * 저장 테스트
	 */
	@DisplayName("insert")
	@Test
	void save() {
		Station expected = new Station("잠실역");
		Station actual = stationRepository.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	/**
	 * 조회 테스트
	 */
	@DisplayName("select : 단건 조회")
	@Test
	void findByName() {
		String expected = "잠실역";
		stationRepository.save(new Station(expected));
		String actual = stationRepository.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("select : 다건 조회")
	@Test
	void findAll() {
		stationRepository.save(new Station("잠실역"));
		stationRepository.save(new Station("몽촌토성역"));
		List<Station> allStation = stationRepository.findAll();
		assertThat(allStation).hasSize(2)
			.anySatisfy(station -> assertThat(station.getName()).isEqualTo("잠실역"))
			.anySatisfy(station -> assertThat(station.getName()).isEqualTo("몽촌토성역"));
	}

	/**
	 * 수정 테스트
	 */
	@DisplayName("update : 쿼리를 호출하지 않아도 자동을 update됨을 확인")
	@Test
	void update() {
		Station station1 = stationRepository.save(new Station("잠실역"));
		station1.changeName("몽촌토성역");
		Station station2 = stationRepository.findByName("몽촌토성역");
		assertThat(station2).isNotNull();
	}

	/**
	 * 삭제 테스트
	 */
	@DisplayName("delete : 기본 delete 메소드로 삭제 테스트")
	@Test
	void delete() {
		Station station1 = stationRepository.save(new Station("잠실역"));
		Station station2 = stationRepository.save(new Station("몽촌토성역"));

		stationRepository.delete(station1);
		assertThat(stationRepository.findAll()).hasSize(1)
			.noneSatisfy(station -> assertThat(station).isEqualTo(station1))
			.anySatisfy(station -> assertThat(station).isEqualTo(station2));
	}
}