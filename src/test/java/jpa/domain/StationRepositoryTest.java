package jpa.domain;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stations;

	@Test
	@DisplayName("레포지토리를 통한 세이브 학습")
	void save_checkProperty() {
		Station expected = new Station("잠실역");
		Station actual = stations.save(expected);
		assertAll(
				() -> assertThat(expected.getId()).isNotNull(),
				() -> assertThat(actual.getId()).isNotNull(),
				() -> assertThat(actual.getId()).isEqualTo(expected.getId()),
				() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	@DisplayName("save 할 때 유니크 키(name) 충돌 테스트")
	void save_duplicatedKey() {
		stations.save(new Station("잠실역"));

		assertThatThrownBy(() -> stations.save(new Station("잠실역")))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("레포지터리에 만든 findByName 테스트")
	void findByName() {
		Station actual = stations.save(new Station("잠실역"));
		Station expected = stations.findByName("잠실역");

		assertAll(
				() -> assertThat(actual.getId()).isEqualTo(expected.getId()),
				() -> assertThat(actual.getName()).isEqualTo("잠실역")
		);
	}
}