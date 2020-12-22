package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Line;
import jpa.domain.Station;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@DataJpaTest
public class StationRepositoryTest {
	@Autowired
	private StationRepository stations;
	@Autowired
	private LineRepository lines;



	@Test
	void save() {
		String stationName = "잠실역";

		Station actual = stations.save(new Station(stationName));

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(stationName),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByName() {
		String stationName = "잠실역";
		stations.save(new Station(stationName));

		Station actual = stations.findByName(stationName);

		assertThat(actual.getName()).isEqualTo(stationName);
	}

	@Test
	void update() {
		String stationName = "잠실역";
		Station savedStation = stations.save(new Station(stationName));

		String expectedName = "몽촌토성역";
		savedStation.changeName(expectedName);

		Station actual = stations.findByName(expectedName);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expectedName)
		);
	}

	@Test
	void delete() {
		String stationName = "잠실역";
		Station savedStation = stations.save(new Station(stationName));

		stations.delete(savedStation);

		Station actual = stations.findByName(stationName);

		assertThat(actual).isNull();

	}

	@DisplayName("지하철 역 조회시 어느 노선에 속한지 볼 수 있다.")
	@Test
	void checkLineWithFindingStation() {
		String lineName = "2호선";
		String lineColor = "green";
		String stationName = "잠실역";


		Station savedStation = saveStation(stationName);
		Line savedLine = saveLine(lineName, lineColor);
		savedStation.changeLine(savedLine);

		Station actual = stations.findByName(savedStation.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(stationName),
			() -> assertThat(actual.getLine()).isEqualTo(savedLine),
			() -> assertThat(actual.getLine().getName()).isEqualTo(lineName),
			() -> assertThat(actual.getLine().getColor()).isEqualTo(lineColor)
		);

	}

	private Station saveStation(String stationName) {
		return stations.save(new Station(stationName));
	}

	private Line saveLine(String lineName, String lineColor) {
		return lines.save(new Line(lineName, lineColor));
	}
}
