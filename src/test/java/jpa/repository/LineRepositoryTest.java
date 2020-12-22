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

	@Test
	void save() {
		String lineName = "2호선";
		String lineColor = "green";

		Line actual = lines.save(new Line(lineName,lineColor));

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(lineName),
			() -> assertThat(actual.getColor()).isEqualTo(lineColor),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByName() {
		String lineName = "2호선";
		String lineColor = "green";

		lines.save(new Line(lineName,lineColor));
		Line actual = lines.findByName(lineName);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(lineName),
			() -> assertThat(actual.getColor()).isEqualTo(lineColor)

		);
	}

	@Test
	void update() {
		String lineName = "2호선";
		String lineColor = "green";

		Line savedLine = lines.save(new Line(lineName, lineColor));
		String expectedColor = "blue";
		savedLine.changeColor(expectedColor);

		Line actual = lines.findByName(lineName);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(lineName),
			() -> assertThat(actual.getColor()).isEqualTo(expectedColor)
		);
	}

	@Test
	void delete() {
		String lineName = "2호선";
		String lineColor = "green";

		Line savedLine = lines.save(new Line(lineName, lineColor));
		lines.delete(savedLine);
		Line deletedLine = lines.findByName(lineName);

		assertThat(deletedLine).isNull();
	}

	@DisplayName("노선 조회 시 속한 지하철 역을 볼 수 있다.")
	@Test
	void addLineWithStation() {
		String lineName = "2호선";
		String lineColor = "green";

		Line savedLine = lines.save(new Line(lineName, lineColor));
		Station station = new Station("강남역");
		savedLine.addStation(station);
		Line actual = lines.findByName(savedLine.getName());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(lineName),
			() -> assertThat(actual.getColor()).isEqualTo(lineColor),
			() -> assertThat(actual.getStations()).contains(station)
		);

	}
}
