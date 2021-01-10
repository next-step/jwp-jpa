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

	@DisplayName("노선에서 이름으로 지하철역 조회")
	@Test
	void given_line_stations_when_find_station_by_name_then_station_equals() {
		final Line line2 = new Line(Color.GREEN, "2호선");
		final Station sadang = new Station("사당역");
		final Station gangnam = new Station("강남역");
		final LineStation line2Sadng = new LineStation(line2, sadang);
		final LineStation lineSGangnam = new LineStation(line2, gangnam);

		Station foundSadang = line2.findStationByName("사당역")
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(line2.getLineStations()).contains(line2Sadng, lineSGangnam),
			() -> assertThat(foundSadang).isEqualTo(sadang)
		);

	}

}
