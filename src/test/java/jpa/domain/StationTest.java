package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

@DataJpaTest
class StationTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	private Station station;
	private Line line;

	@BeforeEach
	void init() {
		line = lineRepository.save(new Line(Color.ORANGE, "3호선"));
		station = stationRepository.save(new Station("교대역", Collections.singletonList(line)));
		lineRepository.flush();
	}

	@Test
	@DisplayName("영속성 컨텍스트: 엔티티 객체 생성 및 동일성 보장 테스트")
	void entitySaveTest() {
		Station expected = stationRepository.save(new Station("시청역"));
		Station actual = stationRepository.findById(expected.getId()).get();

		assertThat(actual).isSameAs(expected);
	}

	@Test
	@DisplayName("영속성 컨텍스트: 변경 감지 테스트")
	void entityChangeTest() {
		Station expected = stationRepository.save(new Station("잠실역"));
		expected.changeName("시청역");

		Station actual = stationRepository.findByName("시청역");

		assertThat(actual).isNotNull();
		assertThat(actual).isSameAs(expected);
	}

	@Test
	@DisplayName("영속성 컨텍스트: 변경 감지, 키 조회 변경 테스트")
	void entityChangeKeyTest() {
		Station expected = stationRepository.save(new Station("잠실역"));
		expected.changeId(3L);

		assertThatExceptionOfType(NoSuchElementException.class)
			.isThrownBy(() -> {
					stationRepository.findById(expected.getId());
				}
			);
	}

	@Test
	@DisplayName("연관 관계: 연관 관계 엔티티 정보 가져오기 테스트")
	void getMappingTest() {
		final Station expected = new Station("잠실역", Collections.singletonList(line));

		Station actual = stationRepository.save(expected);
		System.out.println(actual);
		assertThat(actual.getFirstLine().getName()).isEqualTo("3호선");
	}

	@Test
	@DisplayName("연관 관계: 연관 관계 엔티티 설정 및 저장 에러 테스트")
	void saveErrorWithLineTest() {
		final Station expected = new Station("잠실역");
		expected.setLines(Collections.singletonList(new Line(Color.GREEN, "2호선"))); // 생성한 Line 이 영속 상태가 아니기 떄문에
		stationRepository.save(expected);
		final Station actual = stationRepository.findByName("잠실역");
		assertThat(actual).isNotNull();
		assertThat(expected).isSameAs(actual);
		assertThat(actual.getFirstLine()).isNull();
	}

	@Test
	@DisplayName("연관 관계: 연관 맵핑 엔티티 정보 가져오기 테스트")
	void findByNameWithLine() {
		Station actual = stationRepository.findByName("교대역");

		assertThat(actual).isNotNull();
		assertThat(actual.getFirstLine().getName()).isEqualTo("3호선");
	}

	@Test
	@DisplayName("연관 관계: 맵핑 엔티티 정보 수정 테스트")
	void updateMappingEntityTest() {
		Station expected = stationRepository.findByName("교대역");

		expected.setLines(Arrays.asList(
			lineRepository.save(new Line(Color.GREEN, "2호선")),
			line
		));
		stationRepository.flush();
		// stationRepository.save(expected); // Error

		assertThat(expected.getFirstLine().getName()).isEqualTo("2호선");
		assertThat(expected.getLines().size()).isEqualTo(2);
	}

	@Test
	@DisplayName("연관 관계: 맵핑 엔티티 정보 삭제 테스트")
	void deleteMappingEntityTest() {
		Station expected = stationRepository.findByName("교대역");

		expected.setLines(null);
		stationRepository.flush();
		// stationRepository.save(expected); // Error

		assertThat(expected.getLines()).isNull();
	}

	@Test
	@DisplayName("양방향 연관 관계: 다 대 다(ManyToMany) 양방향 연관 관계, 맵핑된 엔티티 클래스 양방향 조회 장애 테스트")
	void selectJoinErrorEntityTest() {
		final Line lineNumber3 = lineRepository.findByName("3호선");

		assertThat(lineNumber3.getStations().size()).isEqualTo(0);
	}

	@Test
	@DisplayName("양방향 연관 관계: 다 대 다(ManyToMany) 양방향 업데이트 확인 테스트")
	void selectJoinEntityTest() {
		final Line lineNumber3 = lineRepository.findByName("3호선");
		final Station expected = new Station("양재역");

		stationRepository.save(expected);
		lineNumber3.getStations().add(expected);
		stationRepository.flush();

		assertThat(lineNumber3.getStations()).hasSize(1);
	}
}
