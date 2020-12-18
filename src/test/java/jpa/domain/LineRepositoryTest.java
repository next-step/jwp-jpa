package jpa.domain;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

@DataJpaTest
class LineRepositoryTest {
	@Autowired
	private LineRepository lineRepository;

	@Test
	void saveTest() {
		Line expected = new Line("2호선");
		Line actual = lineRepository.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	void updateTest() {
		String changeName = "3호선";
		Line expected = new Line("2호선");
		Line actual = lineRepository.save(expected);
		actual.changeName(changeName);
		assertAll(
			() -> assertThat(lineRepository.findByName("2호선")).isNull(),
			() -> assertThat(lineRepository.findByName(changeName).getName()).isEqualTo(changeName)
		);
	}

	@Test
	void deleteTest() {
		Line expected = new Line("2호선");
		Line actual = lineRepository.save(expected);
		assertThat(actual.getName()).isEqualTo(expected.getName());

		lineRepository.delete(actual);
		assertAll(
			() -> assertThat(lineRepository.findByName("2호선")).isNull(),
			() -> assertThat(lineRepository.findAll().size()).isEqualTo(0)
		);
	}

	@Test
	void findByNameTest() {
		String expected = "2호선";
		lineRepository.save(new Line(expected));
		String actual = lineRepository.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void findByColorTest() {
		String expectedColor = "RED";
		lineRepository.save(new Line("2호선", expectedColor));
		String actual = lineRepository.findByColor(expectedColor).getColor();
		assertThat(actual).isEqualTo(expectedColor);
	}

	@Test
	void findByColorAndNameTest() {
		String expectedName = "2호선";
		String expectedColor = "RED";
		lineRepository.save(new Line(expectedName, expectedColor));
		Line actualEquals = lineRepository.findByNameAndColor(expectedName, expectedColor);
		Line differentName = lineRepository.findByNameAndColor("다른" + expectedName, expectedColor);
		Line differentColor = lineRepository.findByNameAndColor(expectedName, "다른" + expectedColor);

		assertAll(
			() -> assertThat(actualEquals.getName()).isEqualTo(expectedName),
			() -> assertThat(actualEquals.getColor()).isEqualTo(expectedColor),
			() -> assertThat(differentName).isNull(),
			() -> assertThat(differentColor).isNull()
		);
	}

	@Test
	void findByCreatedDateBetweenTest() {
		String expectedName = "2호선";
		String expectedColor = "RED";

		LocalDateTime december17th = LocalDateTime.of(2020, 12, 17, 0, 0, 0);
		LocalDateTime december18th = LocalDateTime.of(2020, 12, 18, 0, 0, 0);
		LocalDateTime december19th = LocalDateTime.of(2020, 12, 19, 0, 0, 0);
		lineRepository.save(new Line(expectedName, expectedColor, december18th));
		Line actualBetween = lineRepository.findByCreatedDateBetween(december17th, december19th);
		Line wrongOrderFromTo = lineRepository.findByCreatedDateBetween(december19th, december17th);

		assertAll(
			() -> assertThat(actualBetween.getName()).isEqualTo(expectedName),
			() -> assertThat(actualBetween.getColor()).isEqualTo(expectedColor),
			() -> assertThat(wrongOrderFromTo).isNull()
		);
	}

	@Test
	void findByLimitTest() {
		LocalDateTime december18th = LocalDateTime.of(2020, 12, 18, 0, 0, 0);

		lineRepository.save(new Line("1호선", "BLUE", december18th));
		lineRepository.save(new Line("2호선", "GREEN", december18th));
		lineRepository.save(new Line("3호선", "ORANGE", december18th));
		lineRepository.save(new Line("4호선", "SKYBLUE", december18th));
		lineRepository.save(new Line("5호선", "PURPLE", december18th));
		lineRepository.save(new Line("6호선", "BROWN", december18th));

		List<Line> allLines = lineRepository.findAll();
		Line firstLineByCreatedDate = lineRepository.findFirstByCreatedDate(december18th);
		List<Line> top3LinesByCreatedDate = lineRepository.findTop3ByCreatedDate(december18th);
		List<Line> top10LinesByCreatedDate = lineRepository.findTop10ByCreatedDate(december18th);

		assertAll(
			// 전체 조회
			() -> assertThat(allLines.size()).isEqualTo(6),
			// 조회 결과가 중복인데 findBy 사용하면 오류
			() -> assertThatThrownBy(() -> lineRepository.findByCreatedDate(december18th)).isInstanceOf(
				IncorrectResultSizeDataAccessException.class),
			// 이때는 findFirst 사용하여야 1개만 가져옴
			() -> assertThat(firstLineByCreatedDate).isNotNull(),
			// findFirst 는 기본적으로 id가 가장 앞인 것을 가져옴
			() -> assertThat(firstLineByCreatedDate.getName()).isEqualTo("1호선"),
			// findTop N 을 사용하면 상위 3개만 조회
			() -> assertThat(top3LinesByCreatedDate.size()).isEqualTo(3),
			// findTop N 이 실제 결과보다 크면 존재하는 만큼만 return
			() -> assertThat(top10LinesByCreatedDate.size()).isEqualTo(6)
		);
	}

	@Test
	void findByOrderByTest() {
		LocalDateTime december18th = LocalDateTime.of(2020, 12, 18, 0, 0, 0);

		lineRepository.save(new Line("1호선", "COLOR-A", december18th));
		lineRepository.save(new Line("2호선", "COLOR-B", december18th));
		lineRepository.save(new Line("3호선", "COLOR-C", december18th));
		lineRepository.save(new Line("4호선", "COLOR-D", december18th));
		lineRepository.save(new Line("5호선", "COLOR-E", december18th));
		lineRepository.save(new Line("6호선", "COLOR-F", december18th));

		List<Line> allLines = lineRepository.findAll();
		List<Line> linesOrderByColor = lineRepository.findByCreatedDateOrderByColor(december18th);
		List<Line> linesOrderByColorDesc = lineRepository.findByCreatedDateOrderByColorDesc(december18th);

		assertAll(
			// 전체 조회
			() -> assertThat(allLines.size()).isEqualTo(6),
			() -> assertThat(allLines.get(0)).isEqualTo(linesOrderByColor.get(0)),
			() -> assertThat(allLines.get(0)).isNotEqualTo(linesOrderByColorDesc.get(0)),
			() -> assertThat(linesOrderByColorDesc.get(0).getColor()).isEqualTo("COLOR-F"),
			() -> assertThat(linesOrderByColorDesc.get(5).getColor()).isEqualTo("COLOR-A")
		);
	}
}