package jpa.repositories;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        lineRepository.save(Line.of("8호선","분홍색"));
    }

    @Test
    @DisplayName("라인 추가")
    void save() {
        // given, when
        Line actual = lineRepository.save(Line.of("2호선","초록색"));

        // then
        lineAssertAll(actual, "2호선", "초록색");
    }

    @Test
    @DisplayName("라인 수정")
    void update() {
        // given
        String changeName = "1호선";

        // when
        Line line = lineRepository.findByName("8호선");
        line.setName(changeName);
        // 수정 확인에서는 사실상 불필요한 코드 테스트 목적 (해당 findByName 전에 flush 진행 후 조회)
        Line actual = lineRepository.findByName("1호선");

        // then
        assertThat(line == actual).isTrue();
        assertThat(actual.getName()).isEqualTo("1호선");
    }

    @Test
    @DisplayName("name unique 속성 체크")
    void uniqueCheck() {
        // given when then
        assertThrows(DataIntegrityViolationException.class, () ->{
            lineRepository.save(Line.of("8호선","분홍색"));
        });
    }

    @Test
    @DisplayName("라인 삭제")
    void delete() {
        // given when
        lineRepository.delete(lineRepository.findByName("8호선"));
        Line actual = lineRepository.findByName("8호선");

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("라인 조회")
    void select() {
        // given when
        Line actual = lineRepository.findByName("8호선");

        // then
        lineAssertAll(actual, "8호선", "분홍색");
    }

    private void lineAssertAll(Line actual,String expectedName, String expectedColor) {
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expectedName),
                () -> assertThat(actual.getColor()).isEqualTo(expectedColor),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("노선 조회 시 속한 지하철역 개수 및 이름 확인")
    void selectRelationMappingLineAndStation() {
        saves();
        Line line7 = lineRepository.findByName("7호선");
        assertThat(line7).isNotNull();
        line7.getLineStations().forEach(lineStation -> {
            MatcherAssert.assertThat(lineStation.getStation().getName(),
                    either(containsString("고속터미널")).or(containsString("반포")));
        });
    }

    private void saves() {
        Line line1 = Line.of("1호선","파란색");
        Line line7 = Line.of("7호선","올리브색");

        Station station_uijeongbu = Station.of("의정부");
        Station station_seoul = Station.of("서울");
        Station station_changdong = Station.of("창동");

        line1.addLineStation(LineStation.of(line1, station_uijeongbu, 10, station_changdong));
        line1.addLineStation(LineStation.of(line1, station_seoul, 15, station_changdong));

        Station station_expressBusTerminal = new Station("고속터미널");
        Station station_banpo = new Station("반포");
        Station station_nowon = new Station("노원");

        line7.addLineStation(LineStation.of(line1, station_expressBusTerminal, 10, station_nowon));
        line7.addLineStation(LineStation.of(line1, station_banpo, 15, station_nowon));

        lineRepository.saveAll(Arrays.asList(line1, line7));
        entityManager.clear();
    }

    @Test
    @DisplayName("중복 된 라인 이름 체크")
    void validationOverlapLineName() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            lineRepository.save(new Line("7호선","올리브색"));
            lineRepository.save(new Line("7호선","올리브색"));
        });
    }
}