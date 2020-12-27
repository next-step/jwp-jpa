package jpa.step1;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.step1.domain.Color;
import jpa.step1.domain.Line;
import jpa.step1.repository.LineRepository;

@DataJpaTest
public class SubwayTest {

	@Autowired
	private LineRepository lineRepository;

	@DisplayName("Line 생성 테스트")
	@Test
	void given_line_when_save_then_return_created_line_with_primary_key() {
		final String lineName = "2호선";

		Line line = new Line(Color.GREEN, lineName);

		Line createdLine = lineRepository.save(line);

		assertThat(createdLine.getId()).isEqualTo(1L);
	}

}
