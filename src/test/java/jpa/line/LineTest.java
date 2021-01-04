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
		Line actual = 전철_노선_구간_생성("1호선", Color.BLUE, "인천", "소요산", 500L);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getPositions()).hasSize(1),
			() -> assertThat(actual.positionsId().get(0)).isNotNull(),
			() -> assertThat(actual.positionsDistance().get(0).getDistance()).isEqualTo(500L)
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

	private Line 전철_노선_구간_생성(String lineName, Color color, String upName, String downName, long distance) {
		// given
		Station upStation = 전철_역_생성(upName);
		Station downStation = 전철_역_생성(downName);

		// when
		return lineRepository.save(new Line(lineName, color, upStation, downStation, distance));
	}

	private Station 전철_역_생성(String name) {
		return stationRepository.save(new Station(name));
	}
}
