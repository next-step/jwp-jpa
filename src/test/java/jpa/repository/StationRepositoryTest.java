package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
		Station expected = new Station("잠실역");

		Station actual = stations.save(expected);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByName() {
		String expected = "잠실역";
		stations.save(new Station(expected));

		String actual = stations.findByName(expected).getName();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void update() {
		Station station = stations.save(new Station("잠실역"));
		String expected = "몽촌토성역";

		station.changeName(expected);

		Station actual = stations.findByName(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected)
		);
	}

	@Test
	void delete() {
		String originName = "잠실역";
		Station station = stations.save(new Station(originName));

		stations.delete(station);

		Station actual = stations.findByName(originName);

		assertThat(actual).isNull();

	}

	@DisplayName("지하철 역 조회시 어느 노선에 속한지 볼 수 있다.")
	@Test
	void checkLineWithFindingStation(){
		String lineName = "2호선";
		String lineColor = "green";
		String stationName = "강남역";

		Line line = lines.save(new Line(lineName, lineColor));
		Station station = new Station(stationName);
		station.changeLine(line);
		stations.save(station);

		Station actual = stations.findByName(stationName);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(stationName),
			() -> assertThat(actual.getLine()).isEqualTo(line),
			() -> assertThat(actual.getLine().getName()).isEqualTo(lineName),
			() -> assertThat(actual.getLine().getColor()).isEqualTo(lineColor)
		);

	}
}
