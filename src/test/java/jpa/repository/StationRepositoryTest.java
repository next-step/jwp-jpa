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

	private static Station PRESET_STATION = new Station("잠실역");


	@DisplayName("지하철 역 조회시 어느 노선에 속한지 볼 수 있다.")
	@Test
	void checkLineWithFindingStation() {
		String lineName = "2호선";
		String lineColor = "green";

		Station savedStation = stations.save(new Station("잠실역"));
		Line line = lines.save(new Line(lineName, lineColor));
		savedStation.changeLine(line);

		Station actual = stations.findByName(savedStation.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(savedStation.getName()),
			() -> assertThat(actual.getLine()).isEqualTo(line),
			() -> assertThat(actual.getLine().getName()).isEqualTo(lineName),
			() -> assertThat(actual.getLine().getColor()).isEqualTo(lineColor)
		);

	}

	@Test
	void save() {

		Station actual = stations.save(PRESET_STATION);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(PRESET_STATION.getName()),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByName() {
		Station savedStation = stations.save(PRESET_STATION);

		Station actual = stations.findByName(savedStation.getName());

		assertThat(actual).isEqualTo(savedStation);
	}

	@Test
	void update() {
		Station station = stations.save(PRESET_STATION);

		String expectedName = "몽촌토성역";
		station.changeName(expectedName);

		Station actual = stations.findByName(expectedName);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expectedName)
		);
	}

	@Test
	void delete() {
		Station savedStation = stations.save(PRESET_STATION);

		stations.delete(savedStation);

		Station actual = stations.findByName(PRESET_STATION.getName());

		assertThat(actual).isNull();

	}

}
