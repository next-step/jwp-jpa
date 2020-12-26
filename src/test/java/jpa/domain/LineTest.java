package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;

@DataJpaTest
class LineTest {
	@Autowired
	private LineRepository lines;

	@DisplayName("Line save 테스트")
	@Test
	public void save() {
		// given
		Line expected = new Line("green", "2호선");

		// when
		Line actual = lines.save(expected);

		// then
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@DisplayName("Line findByColor 테스트")
	@Test
	public void findByColor() {
		// given
		String expected = "green";

		// when
		lines.save(new Line("green", "2호선"));

		// then
		String actual = lines.findByColor(expected).get(0).getColor();
		assertThat(expected).isEqualTo(actual);
	}

	@DisplayName("Line findByName 테스트")
	@Test
	public void findByName() {
		// given
		String expected = "2호선";

		// when
		lines.save(new Line("green", "2호선"));

		// then
		String actual = lines.findByName(expected).getName();
		assertThat(expected).isEqualTo(actual);
	}
}