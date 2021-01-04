package jpa.line;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.position.PositionRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

@DisplayName("지하철 노선 엔티티 테스트")
@DataJpaTest
class LineTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private PositionRepository positionRepository;

	@DisplayName("지하철 노선: 라인 구간위치 추가(전철역 거리 추가 테스트)")
	@Test
	void initLineWithPositionTest() {
		// given // when
		Line actual = 전철_노선_구간_생성("2호선", Color.GREEN, "시청", "서울", 100L);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getPositions()).hasSize(1),
			() -> assertThat(actual.getPositions().get(0).getId()).isNotNull(),
			() -> assertThat(actual.getPositions().get(0).getDistance()).isEqualTo(100L)
		);
	}

	@DisplayName("지하철 노선: 라인 구간 지하철 조회")
	@Test
	void getStationsTest() {
		// given
		Line actual = 전철_노선_구간_생성("2호선", Color.GREEN, "시청", "서울", 100L);

		// when
		List<Station> stations = actual.getStations();

		// then
		assertAll(
			() -> assertThat(stations).isNotNull(),
			() -> assertThat(stations).hasSize(2),
			() -> assertThat(stations.get(0).getName()).isEqualTo("시청"),
			() -> assertThat(stations.get(1).getName()).isEqualTo("서울")
		);
	}

	private Line 전철_노선_구간_생성(String lineName, Color color, String upStation, String downStation, long distance) {
		// given
		Station 시청 = 전철_역_생성(upStation);
		Station 서울 = 전철_역_생성(downStation);

		// when
		return lineRepository.save(new Line(lineName, color, 시청, 서울, 100L));
	}

	private Station 전철_역_생성(String name) {
		return stationRepository.save(new Station(name));
	}
}
