package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LineTest {

	@DisplayName("라인 생성")
	@Test
	void given_parameters_when_new_line_then_return_line() {
		final Color green = Color.GREEN;
		final String line2 = "2호선";

		Line line = new Line(green, line2);

		assertAll(
			() -> assertThat(line).isNotNull(),
			() -> assertThat(line.getColor()).isEqualTo(green),
			() -> assertThat(line.getName()).isEqualTo(line2)
		);

	}

}
