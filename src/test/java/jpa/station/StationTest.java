package jpa.station;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Disabled
@DataJpaTest
class StationTest {

	@Autowired
	private StationRepository stationRepository;

	@BeforeEach
	void init() {
	}

	@DisplayName("전철 역: 생성 테스트")
	@Test
	void initStationTest() {
		// given
		Station expected = new Station("강남역");

		// when
		Station actual = stationRepository.save(expected);

		//then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo("강남역")
		);

	}

	@DisplayName("JPA Auditing: 자동 시간 생성 엔티티 테스트")
	@Test
	void createTimeEntityTest() {

		LocalDateTime now = LocalDateTime.now();

		Station expected = new Station("주안역"); // 영속상태 X
		assertThat(expected.getCreatedDate()).isNull();
		assertThat(expected.getModifiedDate()).isNull();

		Station actual = stationRepository.save(expected);
		assertThat(actual.getCreatedDate()).isNotNull();
		assertThat(actual.getModifiedDate()).isNotNull();

		assertThat(actual.getCreatedDate()).isAfter(now);
		assertThat(actual.getModifiedDate()).isAfter(now);
	}
}
