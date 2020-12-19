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
	void findByLimitTest() {
		String duplicatedColor = "BLUE";
		lineRepository.save(new Line("1호선", duplicatedColor));
		lineRepository.save(new Line("2호선", duplicatedColor));
		lineRepository.save(new Line("3호선", duplicatedColor));
		lineRepository.save(new Line("4호선", duplicatedColor));
		lineRepository.save(new Line("5호선", duplicatedColor));
		lineRepository.save(new Line("6호선", duplicatedColor));

		List<Line> allLines = lineRepository.findAll();
		Line firstLineByColor = lineRepository.findFirstByColor(duplicatedColor);
		List<Line> top3LinesByColor = lineRepository.findTop3ByColor(duplicatedColor);
		List<Line> top10LinesByColor = lineRepository.findTop10ByColor(duplicatedColor);

		assertAll(
			// 전체 조회
			() -> assertThat(allLines.size()).isEqualTo(6),
			// 조회 결과가 중복인데 findBy 사용하면 오류
			() -> assertThatThrownBy(() -> lineRepository.findByColor(duplicatedColor)).isInstanceOf(
				IncorrectResultSizeDataAccessException.class),
			// 이때는 findFirst 사용하여야 1개만 가져옴
			() -> assertThat(firstLineByColor).isNotNull(),
			// findFirst 는 기본적으로 id가 가장 앞인 것을 가져옴
			() -> assertThat(firstLineByColor.getName()).isEqualTo("1호선"),
			// findTop N 을 사용하면 상위 3개만 조회
			() -> assertThat(top3LinesByColor.size()).isEqualTo(3),
			// findTop N 이 실제 결과보다 크면 존재하는 만큼만 return
			() -> assertThat(top10LinesByColor.size()).isEqualTo(6)
		);
	}

	@Test
	void findByOrderByTest() {
		LocalDateTime december18th = LocalDateTime.of(2020, 12, 18, 0, 0, 0);

		lineRepository.save(new Line("1호선", "COLOR-A"));
		lineRepository.save(new Line("2호선", "COLOR-B"));
		lineRepository.save(new Line("3호선", "COLOR-C"));
		lineRepository.save(new Line("4호선", "COLOR-D"));
		lineRepository.save(new Line("5호선", "COLOR-E"));
		lineRepository.save(new Line("6호선", "COLOR-F"));

		List<Line> allLines = lineRepository.findAll();
		List<Line> linesOrderByColor = lineRepository.findAllByOrderByColor();
		List<Line> linesOrderByColorDesc = lineRepository.findAllByOrderByColorDesc();

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