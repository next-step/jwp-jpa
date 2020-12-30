package jpa.repository.step2;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jpa.entity.Line;
import jpa.entity.Station;
import jpa.repository.LineRepository;
import jpa.repository.StationRepository;

@DisplayName("line과 station라는 many to many 연관관계에 대한 CRUD 학습 테스트")
public class LineStationRelationshipTest extends BeforeEachTest {
	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationRepository stationRepository;

	@DisplayName("select : 노선 조회 시 속한 지하철역을 볼 수 있다.")
	@Test
	void selectLine() {
		Set<Station> 이호선_지하철역 = lineRepository.findByColor("초록색").getStations();
		assertThat(이호선_지하철역).hasSize(8);
		assertThat(이호선_지하철역)
			.anySatisfy(지하철역 -> assertThat(지하철역.getName()).isEqualTo("왕십리"))
			.anySatisfy(지하철역 -> assertThat(지하철역.getName()).isEqualTo("신촌"));
	}

	@DisplayName("select : 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
	@Test
	void selectStation() {
		Set<Line> 왕십리_노선 = stationRepository.findByName("왕십리").getLines();
		assertThat(왕십리_노선).hasSize(2);
		assertThat(왕십리_노선)
			.anySatisfy(노선 -> assertThat(노선.getName()).isEqualTo("2호선"))
			.anySatisfy(노선 -> assertThat(노선.getName()).isEqualTo("5호선"));
	}

	@DisplayName("delete : line과 station의 관계만 지우는 방법을 테스트")
	@Test
	void deleteRelationship() {
		Set<Line> 왕십리_노선 = stationRepository.findByName("왕십리").getLines();
		왕십리_노선.removeIf(line -> line.getName().equals("2호선"));
		em.flush();
		em.clear();
		assertThat(stationRepository.findByName("왕십리").getLines()).hasSize(1);
	}

	@DisplayName("delete : line을 바로 지우면 FK로 쓰이는 곳이 있기 때문에 지워지지 않는다")
	@Test
	void deleteLine_shouldException() {
		/**
		 * exception이 발생한다고 해서, manyToMany에 cascade All이나 remove를 걸면 안된다.
		 * 그러면 line을 지울 때 station도 같이 지워지게 된다.
		 */
		assertThatThrownBy(() -> {
			lineRepository.deleteByName("2호선");
			em.flush(); // delete 쿼리를 실행시키기 위함.
		}).isInstanceOf(PersistenceException.class);
	}

	@DisplayName("delete : 연관관계를 먼저 지우고 line을 지우면 잘 지워진다")
	@Test
	void deleteLine() {
		// 연관관계를 지운다
		stationRepository
			.findAllByNameIn("동대문역사문화공원", "을지로4가", "영등포구청", "까치산",
				"왕십리", "충정로", "잠실나루", "신촌")
			.stream()
			.map(Station::getLines)
			.forEach(lines ->
				// manyToMany 특징 : orphanRemoval=true 옵션을 걸지 않았는데도 건 것처럼 동작한다.
				lines.removeIf(line -> "2호선".equals(line.getName())));

		// 연관관계가 존재하지 않기에 지워진다
		lineRepository.deleteByName("2호선");
		assertThat(lineRepository.findByName("2호선")).isNotPresent();
	}
}