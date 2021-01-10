package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Color;
import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;

@DataJpaTest
public class LineStationRepositoryTest {

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineStationRepository lineStationRepository;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	public void setup() {
		Line line2 = new Line(Color.GREEN, "2호선");
		Line line4 = new Line(Color.BLUE, "4호선");

		Station sadang = new Station("사당역");
		Station bangbae = new Station("방배역");
		Station seocho = new Station("서초역");
		Station ieesoo = new Station("이수역");

		LineStation line2Sadang = new LineStation(line2, sadang, bangbae, 10);
		LineStation line2Bangbae = new LineStation(line2, bangbae, sadang, 11);
		LineStation line2Seocho = new LineStation(line2, seocho, bangbae, 11);
		LineStation line4Sadang = new LineStation(line4, sadang, ieesoo, 8);
		LineStation line4Ieesoo = new LineStation(line4, ieesoo, sadang, 8);

		lineRepository.saveAll(Arrays.asList(line2, line4));
		stationRepository.saveAll(Arrays.asList(ieesoo, sadang, bangbae, seocho));

		lineStationRepository.saveAll(Arrays.asList(line2Sadang, line4Sadang));
	}

	@AfterEach
	public void cleanup() {
		this.lineRepository.deleteAll();
		this.stationRepository.deleteAll();
		this.lineStationRepository.deleteAll();
		this.entityManager
			.createNativeQuery("ALTER TABLE line ALTER COLUMN `id` RESTART WITH 1")
			.executeUpdate();

		this.entityManager
			.createNativeQuery("ALTER TABLE station ALTER COLUMN `id` RESTART WITH 1")
			.executeUpdate();

		this.entityManager
			.createNativeQuery("ALTER TABLE line_station ALTER COLUMN `id` RESTART WITH 1")
			.executeUpdate();
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

	@DisplayName("다대다 매핑 테스트")
	@Test
	void given_line_when_linestation_for_each_then_has_many_to_many_relation() {
		Line line2 = lineRepository.findByName("2호선").orElseThrow(IllegalArgumentException::new);

		Station sadang = stationRepository.findByName("사당역")
			.orElseThrow(IllegalArgumentException::new);
		Station bangbae = stationRepository.findByName("방배역")
			.orElseThrow(IllegalArgumentException::new);

		assertThat(line2.getLineStations()).contains(new LineStation(line2, sadang, bangbae, 10),
			new LineStation(line2, bangbae, sadang, 10));
	}

}
