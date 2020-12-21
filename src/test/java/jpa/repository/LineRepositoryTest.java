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

	private Line expected;

	@BeforeEach
	void setUp() {
		expected = new Line("2호선", "green");
	}

	@Test
	void save() {
		Line actual = lines.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByName() {
		lines.save(expected);
		Line actual = lines.findByName(expected.getName());
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor())

		);
	}

	@Test
	void update() {
		Line origin = lines.save(expected);

		String expectedColor = "blue";
		origin.changeColor(expectedColor);

		Line actual = lines.findByName(expected.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expectedColor)
		);
	}

	@Test
	void delete() {
		Line origin = lines.save(expected);

		lines.delete(origin);
		Line deletedLine = lines.findByName(origin.getName());

		assertThat(deletedLine).isNull();
	}

	@DisplayName("노선 조회 시 속한 지하철 역을 볼 수 있다.")
	@Test
	void addLineWithStation() {
		Line line = lines.save(expected);
		Station station = new Station("강남역");
		line.addStation(station);
		Line actual = lines.findByName(expected.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(line.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(line.getColor()),
			() -> assertThat(actual.getStations()).contains(station)
		);

	}
}
