package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StationRepositoryTest {
	public static final String EXAMPLE_STATION_NAME1 = "잠실역";
	public static final String EXAMPLE_STATION_NAME2 = "역삼역";

	@Autowired
	private StationRepository stations;

	@BeforeEach
	void setup() {
		stations.save(new Station(EXAMPLE_STATION_NAME1));
		stations.save(new Station(EXAMPLE_STATION_NAME2));
	}

	@DisplayName("단일 조회 테스트")
	@Test
	void findByName() {
		String expected = EXAMPLE_STATION_NAME1;

		String actual = stations.findByName(expected).getName();

		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("전체 조회 테스트")
	@Test
	void findAll() {
		int expectedLength = 2;

		List<Station> actualAll = stations.findAll();
		List<String> nameAll = actualAll.stream().map(Station::getName).collect(Collectors.toList());

		assertAll(
			() -> assertThat(actualAll).hasSize(expectedLength),
			() -> assertThat(nameAll).contains(EXAMPLE_STATION_NAME1, EXAMPLE_STATION_NAME2)
		);

	}

	@Test
	@DisplayName("insert 테스트")
	void insert() {
		Station expected = new Station("왕십리역");

		Station actual = stations.save(expected);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	@DisplayName("동일한 이름이 insert 되면 DataIntegrityViolationException이 발생한다.")
	void insertDuplicateName() {
		Station newStation = new Station(EXAMPLE_STATION_NAME1);

		assertThatExceptionOfType(DataIntegrityViolationException.class)
			.isThrownBy(() -> {
				stations.save(newStation);
			});
	}

	@Test
	@DisplayName("update 테스트")
	void update() {

		String newName = "사당역";

		Station beforeStation = stations.findByName(EXAMPLE_STATION_NAME1);
		beforeStation.updateName(newName);
		stations.flush();
		Station afterStation = stations.findByName(newName);

		assertAll(
			() -> assertThat(afterStation.getId()).isEqualTo(beforeStation.getId()),
			() -> assertThat(afterStation.getName()).isEqualTo(beforeStation.getName())
		);
	}

	@Test
	@DisplayName("delete 테스트")
	void delete() {
		Station station = stations.findByName(EXAMPLE_STATION_NAME1);
		stations.delete(station);
		Station check = stations.findByName(EXAMPLE_STATION_NAME1);

		assertThat(check).isNull();
	}
}