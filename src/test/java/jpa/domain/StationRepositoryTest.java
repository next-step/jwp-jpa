package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

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
	@Autowired
	private EntityManager entityManager;

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

		station.addLine(line1, 1);
		station.addLine(line2, 2);
		station.addLine(line3, 3);
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

		station.addLine(line1, 1);
		station.addLine(line2, 2);
		// Station을 생성하고 Line 2개를 생성하여 해당 Station에 2개의 Line을 추가했을 때, Line으로 조회해도 해당 Station이 조회되는지 테스트
		// Line을 2개 생성하는 과정에서 1차 캐시에 등록되고 이후에 수정되는 oneToMany관계에 대해 다시 조회해오지 않음
		// 강제로 detach 시키니 재조회를 진행하고 테스트 통과
		entityManager.detach(line1);
		entityManager.detach(line2);

		Station actualStation = stationRepository.saveAndFlush(station);
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

	@Test
	@DisplayName("노선에 역을 추가할 때는 이전 역과 얼마나 차이가 나는지 길이(distance)를 알고 있어야 한다.")
	public void addStationsInLineWithDistanceTest() {
		String expectedName = "문래역";
		Station station = stationRepository.save(new Station(expectedName));

		Line line1 = lineRepository.save(new Line("1호선", "BLUE"));
		Line line2 = lineRepository.save(new Line("2호선", "GREEN"));
		Line line3 = lineRepository.save(new Line("3호선", "ORANGE"));

		station.addLine(line1, 4);
		station.addLine(line2, 2);
		station.addLine(line3, 3);
		Station actualStation = stationRepository.saveAndFlush(station);

		assertAll(
			() -> assertThat(actualStation.getLines().size()).isEqualTo(3),
			() -> assertThat(actualStation.getLines().contains(line1)).isTrue(),
			() -> assertThat(actualStation.getLines().contains(line2)).isTrue(),
			() -> assertThat(actualStation.getLines().contains(line3)).isTrue(),
			() -> assertThat(actualStation.distanceFromPreviousStation(line1)).isEqualTo(4),
			() -> assertThat(actualStation.distanceFromPreviousStation(line2)).isEqualTo(2),
			() -> assertThat(actualStation.distanceFromPreviousStation(line3)).isEqualTo(3)
		);
	}

	@Test
	@DisplayName("지하철역에 이미 존재하는 노선 추가 시, IllegalArgumentException 을 throw 해야한다.")
	public void addExistLinesTest() {
		String expectedStation1Name = "문래역";
		String expectedLineName = "2호선";
		Station station = stationRepository.save(new Station(expectedStation1Name));
		Line line = lineRepository.save(new Line(expectedLineName, "GREEN"));

		station.addLine(line, 0);
		assertThatThrownBy(() -> station.addLine(line, 3))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("지하철역에 존재하지않는 노선의 이전역과의 거리를 구하려고 하면, IllegalArgumentException 을 throw 해야한다.")
	public void getNotExistLineDistanceTest() {
		Station station = stationRepository.save(new Station("문래역"));
		Line line = lineRepository.save(new Line("2호선", "GREEN"));

		assertThatThrownBy(() -> station.distanceFromPreviousStation(line))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
