package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Line;

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
		Line expected = new Line("2호선", "green");
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
		String expectedName = "2호선";
		String expectedColor = "green";
		lines.save(new Line(expectedName, expectedColor));
		Line actual = lines.findByName(expectedName);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expectedName),
			() -> assertThat(actual.getColor()).isEqualTo(expectedColor)

		);
	}

	@Test
	void update() {
		String name = "2호선";
		String color = "green";

		Line origin = lines.save(new Line(name, color));

		String expectedColor = "blue";
		origin.changeColor(expectedColor);

		Line actual = lines.findByName(name);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(name),
			() -> assertThat(actual.getColor()).isEqualTo(expectedColor)
		);
	}

	@Test
	void delete() {
		String name = "2호선";
		String color = "green";

		Line origin = lines.save(new Line(name, color));

		lines.delete(origin);
		Line deletedLine = lines.findByName(name);

		assertThat(deletedLine).isNull();
	}
}
