package jpa.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Color;
import jpa.domain.Line;
import jpa.repository.LineRepository;

@DataJpaTest
public class LineRepositoryTest {

	@Autowired
	private LineRepository lineRepository;

	@DisplayName("Line 생성")
	@Test
	void given_line_when_save_then_return_created_line_with_primary_key() {
		final String lineName = "2호선";
		Line line = new Line(Color.GREEN, lineName);

		Line createdLine = lineRepository.save(line);

		assertThat(createdLine.getId()).isNotNull();
	}

	@DisplayName("Line 조회")
	@Test
	void given_line_when_save_and_getOne_then_return_created_line_with_primary_key() {
		final String lineName = "2호선";
		Line line = new Line(Color.GREEN, lineName);
		lineRepository.save(line);

		Line createdLine = lineRepository.findByName("2호선")
			.orElseThrow(IllegalArgumentException::new);

		assertThat(createdLine.getName()).isEqualTo(lineName);
	}

	@DisplayName("Line 등록시 유니크 키 제약조건 위반시 익셉션")
	@Test
	void given_line_duplicated_name_when_save_then_throw_exception() {
		final String lineName = "2호선";
		Line line = new Line(Color.GREEN, lineName);
		Line duplicatedLine = new Line(Color.GREEN, lineName);
		lineRepository.save(line);

		assertThatThrownBy(() -> lineRepository.save(duplicatedLine));
	}

}
