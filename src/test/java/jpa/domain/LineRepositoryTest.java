package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LineRepositoryTest {
	public static final String EXAMPLE_LINE_1 = "1호선";
	public static final String EXAMPLE_LINE_2 = "2호선";
	public static final String EXAMPLE_RED = "RED";
	public static final String EXAMPLE_GREEN = "GREEN";

	@Autowired
	private LineRepository lines;

	@BeforeEach
	void setup() {
		lines.save(new Line(EXAMPLE_GREEN, EXAMPLE_LINE_1));
		lines.save(new Line(EXAMPLE_RED, EXAMPLE_LINE_2));
	}

	@DisplayName("단일 조회 테스트")
	@Test
	void findByName() {
		String expected = EXAMPLE_LINE_1;

		String actual = lines.findByName(expected).getName();

		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("전체 조회 테스트")
	@Test
	void findAll() {
		int expectedLength = 2;

		List<Line> actualAll = lines.findAll();
		List<String> nameAll = actualAll.stream().map(Line::getName).collect(Collectors.toList());

		assertAll(
			() -> assertThat(actualAll).hasSize(expectedLength),
			() -> assertThat(nameAll).contains(EXAMPLE_LINE_1, EXAMPLE_LINE_2)
		);

	}

	@Test
	@DisplayName("insert 테스트")
	void insert() {
		Line expected = new Line("CYAN", "3호선");

		Line actual = lines.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
		);
	}

	@Test
	@DisplayName("동일한 이름이 insert 되면 DataIntegrityViolationException이 발생한다.")
	void insertDuplicateName() {
		Line newLine = new Line("CYAN", EXAMPLE_LINE_1);

		assertThatExceptionOfType(DataIntegrityViolationException.class)
			.isThrownBy(() -> {
				lines.save(newLine);
			});
	}

	@Test
	@DisplayName("update 테스트")
	void update() {
		String expected = EXAMPLE_LINE_1;

		Line line = lines.findByName(EXAMPLE_LINE_1);
		line.updateName(expected);
		lines.flush();
		Line check = lines.findByName(expected);

		assertAll(
			() -> assertThat(check.getId()).isEqualTo(line.getId()),
			() -> assertThat(check.getName()).isEqualTo(line.getName())
		);
	}

	@Test
	@DisplayName("delete 테스트")
	void delete() {
		Line line = lines.findByName(EXAMPLE_LINE_1);
		lines.delete(line);
		Line check = lines.findByName(EXAMPLE_LINE_1);

		assertThat(check).isNull();
	}
}