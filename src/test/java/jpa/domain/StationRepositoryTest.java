package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
class StationRepositoryTest {
	@Autowired
	private StationRepository stations;
	@Autowired
	private LineRepository lines;

	@Test
	@DisplayName("Station을 저장한다.")
	void save_test() {
		Station expected = new Station("잠실역");
		assertThat(expected.getId()).isNull();

		Station actual = stations.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	@DisplayName("같은 이름의 Station을 저장하면 오류가 발생한다.")
	void save_unique_test() {
		Station station1 = new Station("잠실역");
		Station station2 = new Station("잠실역");
		stations.save(station1);
		assertThatExceptionOfType(DataIntegrityViolationException.class)
			.isThrownBy(() -> stations.save(station2));
	}

	@Test
	@DisplayName("이름으로 Station을 가져온다.")
	void findByName_test() {
		String expected = "잠실역";
		stations.save(new Station(expected));
		String actual = stations.findByName(expected).map(Station::getName).orElse("");
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DisplayName("Station의 이름을 변경한다.")
	void changeName_test() {
		Station expected = stations.save(new Station("잠실역"));
		expected.changeName("역삼역");
		Station actual = stations.findByName("역삼역").orElse(null);

		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(expected.getId());
	}

	@Test
	@DisplayName("date 필드가 자동으로 입력되는지 확인한다.")
	void date_test() {
		Station station = stations.save(new Station("잠실역"));
		Station actual = stations.findById(station.getId()).orElse(new Station());

		assertThat(actual.getCreatedDate()).isNotNull();
		assertThat(actual.getModifiedDate()).isNotNull();
	}

	@Test
	@DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
	void getLine_test() {
		Station station = stations.findByName("당산역").orElse(new Station());
		Line lineTow = lines.findByName("2호선").orElse(new Line());
		Line lineNine = lines.findByName("9호선").orElse(new Line());

		assertThat(station.getLines())
			.hasSize(2)
			.contains(lineTow, lineNine);
	}
}