package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {

	@Autowired
	LineRepository lines;

	@DisplayName("라인 저장 테스트")
	@Test
	void save() {
		Line expected = new Line("신분당선", "red");
		Line actual = lines.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
		);
	}

	@DisplayName("라인 조회 테스트")
	@Test
	void findByName() {
		String expected = "신분당선";
		lines.save(new Line("신분당선", "red"));
		String actualName = lines.findByName(expected).getName();
		assertThat(actualName).isEqualTo(expected);
	}

	@DisplayName("같은 색 라인 조회 테스트")
	@Test
	void findByColor() {
		lines.save(new Line("신분당선", "red"));
		lines.save(new Line("5호선", "red"));

		List<Line> red = lines.findByColor("red");

		assertThat(red.size()).isEqualTo(2);
	}
}
