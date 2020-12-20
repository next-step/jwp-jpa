package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stationRepository;
	@Autowired
	private LineRepository lineRepository;

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

	@Test
	@DisplayName("지하철역 조회 시 속한 노선을을 볼 수 있다.")
	public void stationsInLineTest() {
		String expectedName = "문래역";
		Station station = stationRepository.save(new Station(expectedName));

		Line line1 = lineRepository.save(new Line("1호선", "BLUE"));
		Line line2 = lineRepository.save(new Line("2호선", "GREEN"));
		Line line3 = lineRepository.save(new Line("3호선", "ORANGE"));
		Line line4 = lineRepository.save(new Line("4호선", "SKYBLUE"));

		station.addLines(Arrays.asList(line1, line2, line3));
		Station actual = stationRepository.findByName(expectedName);

		assertAll(
			() -> assertThat(actual.getLines().size()).isEqualTo(3),
			() -> assertThat(actual.getLines().contains(line1)).isTrue(),
			() -> assertThat(actual.getLines().contains(line2)).isTrue(),
			() -> assertThat(actual.getLines().contains(line3)).isTrue(),
			() -> assertThat(actual.getLines().contains(line4)).isFalse()
		);
	}

	@Test
	@DisplayName("지하철역에 노선 추가 시, 노선에도 지하철역이 추가되어야한다.")
	public void addStationsInLineTest() {
		String expectedStationName = "당산역";
		String expectedLine1Name = "2호선";
		String expectedLine2Name = "9호선";
		Station station = stationRepository.save(new Station(expectedStationName));
		Line line1 = lineRepository.save(new Line(expectedLine1Name, "GREEN"));
		Line line2 = lineRepository.save(new Line(expectedLine2Name, "GOLD"));

		assertThat(station.getLines().size()).isZero();
		assertThat(line1.getStations().size()).isZero();
		assertThat(line2.getStations().size()).isZero();

		station.addLines(Arrays.asList(line1, line2));

		Station actualStation = stationRepository.findByName(expectedStationName);
		Line actualLine1 = lineRepository.findByName(expectedLine1Name);
		Line actualLine2 = lineRepository.findByName(expectedLine2Name);

		assertAll(
			() -> assertThat(actualStation.getLines().size()).isEqualTo(2),
			() -> assertThat(actualStation.getLines().contains(actualLine1)).isTrue(),
			() -> assertThat(actualStation.getLines().contains(actualLine2)).isTrue(),
			() -> assertThat(actualLine1.getStations().size()).isEqualTo(1),
			() -> assertThat(actualLine1.getStations().contains(actualStation)).isTrue(),
			() -> assertThat(actualLine2.getStations().size()).isEqualTo(1),
			() -> assertThat(actualLine2.getStations().contains(actualStation)).isTrue()
		);
	}
}
