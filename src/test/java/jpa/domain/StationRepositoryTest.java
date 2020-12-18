package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import jpa.domain.entity.Line;
import jpa.domain.entity.LineStation;
import jpa.domain.entity.Station;
import jpa.domain.repository.LineRepository;
import jpa.domain.repository.StationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StationRepositoryTest {
	public static final String EXAMPLE_STATION_NAME1 = "신도림";
	public static final String EXAMPLE_STATION_NAME2 = "역삼역";
	public static final String LINE_NAME1 = "1호선";
	public static final String LINE_NAME2 = "2호선";

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@BeforeEach
	void setup() {
		Line line1 = Line.create("RED", LINE_NAME1);
		Line line2 = Line.create("GREEN", LINE_NAME2);

		stationRepository.save(Station.create(EXAMPLE_STATION_NAME1, Arrays.asList(line1, line2)));
		stationRepository.save(Station.create(EXAMPLE_STATION_NAME2, Arrays.asList(line2)));
	}

	@DisplayName("단일 조회 테스트")
	@Test
	void findByName() {
		String expected = EXAMPLE_STATION_NAME1;

		String actual = stationRepository.findByName(expected).getName();

		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("전체 조회 테스트")
	@Test
	void findAll() {
		int expectedLength = 2;

		List<Station> actualAll = stationRepository.findAll();
		List<String> nameAll = actualAll.stream().map(Station::getName).collect(Collectors.toList());

		assertAll(
			() -> assertThat(actualAll).hasSize(expectedLength),
			() -> assertThat(nameAll).contains(EXAMPLE_STATION_NAME1, EXAMPLE_STATION_NAME2)
		);

	}

	@Test
	@DisplayName("insert 테스트")
	void insert() {
		Station expected = Station.create("왕십리역");

		Station actual = stationRepository.save(expected);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	@DisplayName("동일한 이름이 insert 되면 DataIntegrityViolationException이 발생한다.")
	void insertDuplicateName() {
		Station newStation = Station.create(EXAMPLE_STATION_NAME1);

		assertThatExceptionOfType(DataIntegrityViolationException.class)
			.isThrownBy(() -> {
				stationRepository.save(newStation);
			});
	}

	@Test
	@DisplayName("update 테스트")
	void update() {

		String newName = "사당역";

		Station beforeStation = stationRepository.findByName(EXAMPLE_STATION_NAME1);
		beforeStation.updateName(newName);
		stationRepository.flush();
		Station afterStation = stationRepository.findByName(newName);

		assertAll(
			() -> assertThat(afterStation.getId()).isEqualTo(beforeStation.getId()),
			() -> assertThat(afterStation.getName()).isEqualTo(beforeStation.getName())
		);
	}

	@Test
	@DisplayName("delete 테스트")
	void delete() {
		Station station = stationRepository.findByName(EXAMPLE_STATION_NAME1);
		stationRepository.delete(station);
		Station check = stationRepository.findByName(EXAMPLE_STATION_NAME1);

		assertThat(check).isNull();
	}

	@Test
	@DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
	void lineStation() {

		Station station = stationRepository.findByName(EXAMPLE_STATION_NAME1);
		List<LineStation> lineStations = station.getLineStations();
		List<Line> lines = CollectionUtils.emptyIfNull(lineStations).stream()
			.map(LineStation::getLine)
			.collect(Collectors.toList());
		List<String> lineNames = CollectionUtils.emptyIfNull(lines).stream()
			.map(Line::getName)
			.collect(Collectors.toList());

		assertAll(
			() -> assertThat(lines).hasSize(2),
			() -> assertThat(lineNames).contains(LINE_NAME1, LINE_NAME2)
		);
	}
}