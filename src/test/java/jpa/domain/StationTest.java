package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StationTest {

	@DisplayName("지하철역 생성")
	@Test
	void given_parameters_when_new_station_then_return_station() {
		final String name = "사당역";

		final Station sadang = new Station(name);

		assertAll(
			() -> assertThat(sadang).isNotNull(),
			() -> assertThat(sadang.getName()).isEqualTo(name)
		);

	}

}
