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
		final Station bangbae = new Station("방배역");
		final Station gangnam = new Station("강남역");
		final Station seocho = new Station("서초역");
		final LineStation line2Sadng = new LineStation(line2, sadang, bangbae, 10);
		final LineStation lineSGangnam = new LineStation(line2, gangnam, seocho, 8);

		Station foundSadang = line2.findStationByName("사당역")
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(line2.getLineStations()).contains(line2Sadng, lineSGangnam),
			() -> assertThat(foundSadang).isEqualTo(sadang)
		);

	}

	@DisplayName("노선에 역을 추가할 때는 이전 역과 얼마나 차이가 나는지 길이(distance)를 알고 있어야 한다.")
	@Test
	void distance() {
		final Line line2 = new Line(Color.GREEN, "2호선");
		final Station sadang = new Station("사당역");
		final Station bangbae = new Station("방배역");
		final int expectedDistacne = 10;
		final LineStation line2Sadang = new LineStation(line2, sadang, bangbae, expectedDistacne);

		final Station foundSadang = line2.findStationByName("사당역")
			.orElseThrow(IllegalArgumentException::new);

		assertThat(foundSadang.getDistance(line2)).isEqualTo(expectedDistacne);
	}


}
