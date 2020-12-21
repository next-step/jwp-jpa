package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

@DataJpaTest
class LineRepositoryTest {

	@Autowired
	private LineRepository lineRepository;
	@Autowired
	private StationRepository stationRepository;
	@Autowired
	private EntityManager entityManager;

	@Test
	void saveTest() {
		Line expected = new Line("2호선");
		Line actual = lineRepository.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	void updateTest() {
		String changeName = "3호선";
		Line expected = new Line("2호선");
		Line actual = lineRepository.save(expected);
		actual.changeName(changeName);
		assertAll(
			() -> assertThat(lineRepository.findByName("2호선")).isNull(),
			() -> assertThat(lineRepository.findByName(changeName).getName()).isEqualTo(changeName)
		);
	}

	@Test
	void deleteTest() {
		Line expected = new Line("2호선");
		Line actual = lineRepository.save(expected);
		assertThat(actual.getName()).isEqualTo(expected.getName());

		lineRepository.delete(actual);
		assertAll(
			() -> assertThat(lineRepository.findByName("2호선")).isNull(),
			() -> assertThat(lineRepository.findAll().size()).isEqualTo(0)
		);
	}

	@Test
	void findByNameTest() {
		String expected = "2호선";
		lineRepository.save(new Line(expected));
		String actual = lineRepository.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void findByColorTest() {
		String expectedColor = "RED";
		lineRepository.save(new Line("2호선", expectedColor));
		String actual = lineRepository.findByColor(expectedColor).getColor();
		assertThat(actual).isEqualTo(expectedColor);
	}

	@Test
	void findByColorAndNameTest() {
		String expectedName = "2호선";
		String expectedColor = "RED";
		lineRepository.save(new Line(expectedName, expectedColor));
		Line actualEquals = lineRepository.findByNameAndColor(expectedName, expectedColor);
		Line differentName = lineRepository.findByNameAndColor("다른" + expectedName, expectedColor);
		Line differentColor = lineRepository.findByNameAndColor(expectedName, "다른" + expectedColor);

		assertAll(
			() -> assertThat(actualEquals.getName()).isEqualTo(expectedName),
			() -> assertThat(actualEquals.getColor()).isEqualTo(expectedColor),
			() -> assertThat(differentName).isNull(),
			() -> assertThat(differentColor).isNull()
		);
	}

	@Test
	void findByLimitTest() {
		String duplicatedColor = "BLUE";
		lineRepository.save(new Line("1호선", duplicatedColor));
		lineRepository.save(new Line("2호선", duplicatedColor));
		lineRepository.save(new Line("3호선", duplicatedColor));
		lineRepository.save(new Line("4호선", duplicatedColor));
		lineRepository.save(new Line("5호선", duplicatedColor));
		lineRepository.save(new Line("6호선", duplicatedColor));

		List<Line> allLines = lineRepository.findAll();
		Line firstLineByColor = lineRepository.findFirstByColor(duplicatedColor);
		List<Line> top3LinesByColor = lineRepository.findTop3ByColor(duplicatedColor);
		List<Line> top10LinesByColor = lineRepository.findTop10ByColor(duplicatedColor);

		assertAll(
			// 전체 조회
			() -> assertThat(allLines.size()).isEqualTo(6),
			// 조회 결과가 중복인데 findBy 사용하면 오류
			() -> assertThatThrownBy(() -> lineRepository.findByColor(duplicatedColor)).isInstanceOf(
				IncorrectResultSizeDataAccessException.class),
			// 이때는 findFirst 사용하여야 1개만 가져옴
			() -> assertThat(firstLineByColor).isNotNull(),
			// findFirst 는 기본적으로 id가 가장 앞인 것을 가져옴
			() -> assertThat(firstLineByColor.getName()).isEqualTo("1호선"),
			// findTop N 을 사용하면 상위 3개만 조회
			() -> assertThat(top3LinesByColor.size()).isEqualTo(3),
			// findTop N 이 실제 결과보다 크면 존재하는 만큼만 return
			() -> assertThat(top10LinesByColor.size()).isEqualTo(6)
		);
	}

	@Test
	void findByOrderByTest() {
		LocalDateTime december18th = LocalDateTime.of(2020, 12, 18, 0, 0, 0);

		lineRepository.save(new Line("1호선", "COLOR-A"));
		lineRepository.save(new Line("2호선", "COLOR-B"));
		lineRepository.save(new Line("3호선", "COLOR-C"));
		lineRepository.save(new Line("4호선", "COLOR-D"));
		lineRepository.save(new Line("5호선", "COLOR-E"));
		lineRepository.save(new Line("6호선", "COLOR-F"));

		List<Line> allLines = lineRepository.findAll();
		List<Line> linesOrderByColor = lineRepository.findAllByOrderByColor();
		List<Line> linesOrderByColorDesc = lineRepository.findAllByOrderByColorDesc();

		assertAll(
			// 전체 조회
			() -> assertThat(allLines.size()).isEqualTo(6),
			() -> assertThat(allLines.get(0)).isEqualTo(linesOrderByColor.get(0)),
			() -> assertThat(allLines.get(0)).isNotEqualTo(linesOrderByColorDesc.get(0)),
			() -> assertThat(linesOrderByColorDesc.get(0).getColor()).isEqualTo("COLOR-F"),
			() -> assertThat(linesOrderByColorDesc.get(5).getColor()).isEqualTo("COLOR-A")
		);
	}

	@Test
	@DisplayName("노선 조회 시 속한 지하철역을 볼 수 있다.")
	public void stationsInLineTest() {
		String expectedName = "2호선";
		Line line = lineRepository.save(new Line(expectedName, "GREEN"));

		Station station1 = stationRepository.save(new Station("문래역"));
		Station station2 = stationRepository.save(new Station("구로디지털단지역"));
		Station station3 = stationRepository.save(new Station("잠실역"));

		line.addStation(station1, 0);
		line.addStation(station2, 3);
		line.addStation(station3, 2);
		Line actual = lineRepository.findByName(expectedName);

		assertAll(
			() -> assertThat(actual.getStations().size()).isEqualTo(3),
			() -> assertThat(actual.getStations().contains(station1)).isTrue(),
			() -> assertThat(actual.getStations().contains(station2)).isTrue(),
			() -> assertThat(actual.getStations().contains(station3)).isTrue()
		);
	}

	@Test
	@DisplayName("노선에 지하철역 추가 시, 지하철역에도 노선이 추가되어야한다.")
	public void addStationsInLineTest() {
		String expectedLineName = "2호선";
		String expectedStation1Name = "문래역";
		String expectedStation2Name = "구로디지털단지역";
		Line line = lineRepository.save(new Line(expectedLineName, "GREEN"));
		Station station1 = stationRepository.save(new Station(expectedStation1Name));
		Station station2 = stationRepository.save(new Station(expectedStation2Name));

		assertThat(line.getStations().size()).isZero();
		assertThat(station1.getLines().size()).isZero();
		assertThat(station2.getLines().size()).isZero();

		line.addStation(station1, 0);
		line.addStation(station2, 3);

		// Line을 생성하고 Station2개를 생성하여 해당 Line에 2개의 Station을 추가했을 때, Station으로 조회해도 해당 Line이 조회되는지 테스트
		// Station을 2개 생성하는 과정에서 1차 캐시에 등록되고 이후에 수정되는 oneToMany관계에 대해 다시 조회해오지 않음
		// 강제로 detach 시키니 재조회를 진행하고 테스트 통과
		entityManager.detach(station1);
		entityManager.detach(station2);

		Line actualLine = lineRepository.findByName(expectedLineName);
		Station actualStation1 = stationRepository.findByName(expectedStation1Name);
		Station actualStation2 = stationRepository.findByName(expectedStation2Name);

		assertAll(
			() -> assertThat(actualLine.getStations().size()).isEqualTo(2),
			() -> assertThat(actualLine.getStations().contains(station1)).isTrue(),
			() -> assertThat(actualLine.getStations().contains(station2)).isTrue(),
			() -> assertThat(actualStation1.getLines().size()).isEqualTo(1),
			() -> assertThat(actualStation1.getLines().contains(actualLine)).isTrue(),
			() -> assertThat(actualStation2.getLines().size()).isEqualTo(1),
			() -> assertThat(actualStation2.getLines().contains(actualLine)).isTrue()
		);
	}

	@Test
	@DisplayName("노선에 역을 추가할 때는 이전 역과 얼마나 차이가 나는지 길이(distance)를 알고 있어야 한다.")
	public void addStationsInLineWithDistanceTest() {
		String expectedName = "2호선";
		Line line = lineRepository.save(new Line(expectedName, "GREEN"));

		Station station1 = stationRepository.save(new Station("문래역"));
		Station station2 = stationRepository.save(new Station("구로디지털단지역"));
		Station station3 = stationRepository.save(new Station("잠실역"));

		line.addStation(station1, 0);
		line.addStation(station2, 3);
		line.addStation(station3, 2);
		Line actual = lineRepository.findByName(expectedName);

		assertAll(
			() -> assertThat(actual.getStations().size()).isEqualTo(3),
			() -> assertThat(actual.getStations().contains(station1)).isTrue(),
			() -> assertThat(actual.getStations().contains(station2)).isTrue(),
			() -> assertThat(actual.getStations().contains(station3)).isTrue(),
			() -> assertThat(actual.distanceFromPreviousStation(station1)).isEqualTo(0),
			() -> assertThat(actual.distanceFromPreviousStation(station2)).isEqualTo(3),
			() -> assertThat(actual.distanceFromPreviousStation(station3)).isEqualTo(2)
		);
	}

	@Test
	@DisplayName("노선에 이미 존재하는 지하철역 추가 시, IllegalArgumentException 을 throw 해야한다.")
	public void addExistStationsInLineTest() {
		String expectedLineName = "2호선";
		String expectedStation1Name = "문래역";
		Line line = lineRepository.save(new Line(expectedLineName, "GREEN"));
		Station station = stationRepository.save(new Station(expectedStation1Name));

		line.addStation(station, 0);
		assertThatThrownBy(() -> line.addStation(station, 3))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("노선에 존재하지않는 지하철역의 이전역과의 거리를 구하려고 하면, IllegalArgumentException 을 throw 해야한다.")
	public void getNotExistStationDistanceTest() {
		Line line = lineRepository.save(new Line("2호선", "GREEN"));
		Station station = stationRepository.save(new Station("문래역"));

		assertThatThrownBy(() -> line.distanceFromPreviousStation(station))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
