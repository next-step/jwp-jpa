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
public class LineRepositoryTest {
	@Autowired
	private LineRepository lines;

	private static Line PRESET_LINE = new Line("2호선", "green");

	private static Station PRESET_STATION = new Station("강남역");



	@Test
	void save() {
		Line actual = lines.save(PRESET_LINE);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(PRESET_LINE.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(PRESET_LINE.getColor()),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByName() {
		Line savedLine = lines.save(PRESET_LINE);
		Line actual = lines.findByName(savedLine.getName());
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(savedLine.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(savedLine.getColor())

		);
	}

	@Test
	void update() {
		Line savedLine = lines.save(PRESET_LINE);

		String expectedColor = "blue";
		savedLine.changeColor(expectedColor);

		Line actual = lines.findByName(savedLine.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(savedLine.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expectedColor)
		);
	}

	@Test
	void delete() {
		Line savedLine = lines.save(PRESET_LINE);

		lines.delete(savedLine);
		Line deletedLine = lines.findByName(PRESET_LINE.getName());

		assertThat(deletedLine).isNull();
	}

	@DisplayName("노선 조회 시 속한 지하철 역을 볼 수 있다.")
	@Test
	void addLineWithStation() {
		Line savedLine = lines.save(PRESET_LINE);

		savedLine.addStation(PRESET_STATION);
		Line actual = lines.findByName(savedLine.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(savedLine.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(savedLine.getColor()),
			() -> assertThat(actual.getStations()).contains(PRESET_STATION)
		);

	}
}
