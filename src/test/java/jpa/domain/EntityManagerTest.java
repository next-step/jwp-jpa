package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EntityManagerTest {

	@Autowired
	private StationRepository stations;

	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("영속성 관리하는 객체의 instance 동일성 보장")
	void instanceEqual() {
		Station actual = stations.save(new Station("잠실역"));
		Station expected1 = stations.findById(actual.getId()).get();
		Station expected2 = stations.findByName("잠실역");

		assertThat(actual == expected1).isTrue();
		assertThat(actual == expected2).isTrue();
	}

	@Test
	@DisplayName("영속성 컨텍스트에서 detach 되고 다시 retrieve 될경우 instance 의 동일성 테스트")
	void instanceEqual_AfterDetach() {
		Station actual = stations.save(new Station("잠실역"));
		entityManager.detach(actual);
		Station expected = stations.findByName("잠실역");

		assertThat(expected == actual).isFalse();
	}

	@Test
	@DisplayName("변경감지 테스트")
	void update() {
		Station station1 = stations.save(new Station("잠실역"));
		station1.setName("몽촌토성역");
		Station station2 = stations.findByName("몽촌토성역");

		assertThat(station2).isNotNull();
		assertThat(station1==station2).isTrue();
	}

	@Test
	@DisplayName("준영속상태의 변경감지 테스트")
	void update_AfterDetach() {
		Station station1 = stations.save(new Station("잠실역"));
		entityManager.detach(station1);
		station1.setName("몽촌토성역");

		assertThat(stations.findByName("몽촌토성역")).isNull();
		assertThat(stations.findByName("잠실역").getId()).isEqualTo(station1.getId());
	}
}
