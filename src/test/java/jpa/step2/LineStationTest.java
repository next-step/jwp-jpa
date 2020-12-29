package jpa.step2;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.step1.domain.Color;
import jpa.step1.domain.Line;
import jpa.step1.domain.Station;
import jpa.step1.repository.LineRepository;
import jpa.step1.repository.StationRepository;
import jpa.step2.domain.LineStation;
import jpa.step2.repository.LineStationRepository;

@DataJpaTest
public class LineStationTest {

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineStationRepository lineStationRepository;

	@BeforeEach
	public void setup() {
		Line line2 = new Line(Color.GREEN, "2호선");
		Line line4 = new Line(Color.BLUE, "4호선");

		Station sadang = new Station("사당역");
		Station bangbae = new Station("방배역");
		Station seocho = new Station("서초역");
		Station ieesoo = new Station("이수역");

		LineStation line2Sadang = new LineStation(line2, sadang);
		LineStation line2Bangbae = new LineStation(line2, bangbae);
		LineStation line2Seocho = new LineStation(line2, seocho);
		LineStation line4Sadang = new LineStation(line4, sadang);
		LineStation line4Ieesoo = new LineStation(line4, ieesoo);

		lineRepository.saveAll(Arrays.asList(line2, line4));
		stationRepository.saveAll(Arrays.asList(ieesoo, sadang, bangbae, seocho));

		lineStationRepository.saveAll(Arrays.asList(line2Sadang, line4Sadang));
	}

	@DisplayName("노선 조회 시 속한 지하철역 조회")
	@Test
	void given_line_station_when_foreach_then_has_line() {
		Line line2 = lineRepository.findByName("2호선").orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> line2.getLineStations().forEach(lineStation -> {
				assertThat(lineStation.getLine()).isEqualTo(line2);
				assertThat(lineStation.getStation()).isNotNull();
			}),
			() -> assertThat(line2.getLineStations()).hasSize(3)
		);
	}

	@DisplayName("지하철역 조회 시 어느 노선에 속한지 조회")
	@Test
	void given_station_when_linestation_for_each_then_has_line() {
		Station sadang = stationRepository.findByName("사당역")
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> sadang.getLineStations().forEach(lineStation -> {
				assertThat(lineStation.getLine()).isNotNull();
				assertThat(lineStation.getStation()).isEqualTo(sadang);
			}),
			() -> assertThat(sadang.getLineStations()).hasSize(2)
		);
	}

}
