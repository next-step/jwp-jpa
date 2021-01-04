package jpa.repository.step2and3;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jpa.entity.Line;
import jpa.entity.LineStation;
import jpa.entity.LineStationPk;
import jpa.entity.Station;
import jpa.repository.LineRepository;
import jpa.repository.LineStationRepository;
import jpa.repository.StationRepository;

@DisplayName("line과 station라는 manyToMany 연관관계를 OneToMany 두개로 변경한 후의 학습 테스트")
public class LineStationRelationshipTest extends BeforeEachTest {
	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineStationRepository lineStationRepository;

	@DisplayName("select : 노선 조회 시 속한 지하철역을 볼 수 있다.")
	@Test
	void selectLine() {
		Set<Station> 이호선_지하철역 = lineRepository.findByName("2호선").get()
			.getLineStations()
			.stream()
			.map(LineStation::getStation)
			.collect(Collectors.toSet());
		assertThat(이호선_지하철역).hasSize(7);
		assertThat(이호선_지하철역)
			.anySatisfy(지하철역 -> assertThat(지하철역.getName()).isEqualTo("왕십리"))
			.anySatisfy(지하철역 -> assertThat(지하철역.getName()).isEqualTo("신촌"));
	}

	@DisplayName("select : 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
	@Test
	void selectStation() {
		Set<Line> 왕십리_노선 = stationRepository.findByName("왕십리")
			.getLineStations()
			.stream()
			.map(LineStation::getLine)
			.collect(Collectors.toSet());
		assertThat(왕십리_노선).hasSize(2);
		assertThat(왕십리_노선)
			.anySatisfy(노선 -> assertThat(노선.getName()).isEqualTo("2호선"))
			.anySatisfy(노선 -> assertThat(노선.getName()).isEqualTo("5호선"));
	}

	@DisplayName("select : 역에서부터 역까지의 거리를 합산하여 구할 수 있다")
	@Test
	void selectDistance() {
		Line line = lineRepository.findByName("5호선").get();
		Station start = stationRepository.findByName("을지로4가");
		Station end = stationRepository.findByName("강동");
		assertThat(getDistance(line, start, end)).isEqualTo(23);
	}

	private int getDistance(Line 노선, Station 시작역, Station 마지막역) {
		int 길이 = 0;
		while (시작역 != 마지막역) {
			Optional<LineStation> lineStationOptional = lineStationRepository.findById(LineStationPk.of(노선, 마지막역));
			if (lineStationOptional.isPresent()) {
				LineStation lineStation = lineStationOptional.get();
				길이 += lineStation.getUpDistance();
				마지막역 = lineStation.getUpStation();
			} else {
				break;
			}
		}
		return 길이;
	}

	@DisplayName("delete : orphanRemoval 옵션을 통해 동작하는 delete. line과 station 한군데만 해당 옵션이 있어도 된다.")
	@Test
	void deleteRelationship() {
		Line 이호선 = lineRepository.findByName("2호선").get();
		Station 왕십리 = stationRepository.findByName("왕십리");
		왕십리.removeLineStation(lineStationRepository.findById(LineStationPk.of(이호선, 왕십리)).get());
		em.flush(); // 생략 시 에러
		em.clear();
		assertThat(lineRepository.findByName("2호선").get().getLineStations()).hasSize(6);
	}

	@DisplayName("delete : Cascade 또는 orphanRemoval 옵션으로 인해 부모를 지우면 자식도 지워진다. (둘 중 하나만 있어도 동작)")
	@Test
	void deleteLine() {
		Line 이호선 = lineRepository.findByName("2호선").get();
		lineRepository.deleteByName("2호선");
		assertThat(lineStationRepository.findByLine(이호선)).isNotPresent();
	}

	@DisplayName("delete : LineStationRepository를 통해 연관관계를 직접 지울 수 있다")
	@Test
	void deleteLine_shouldException() {
		Line 이호선 = lineRepository.findByName("2호선").get();
		Station 왕십리 = stationRepository.findByName("왕십리");
		lineStationRepository.deleteById(LineStationPk.of(이호선, 왕십리));
		em.flush(); // 생략 시 에러
		em.clear();
		assertThat(lineRepository.findByColor("초록색").getLineStations()).hasSize(6);
	}
}