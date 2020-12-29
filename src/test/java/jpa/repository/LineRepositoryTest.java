package jpa.repository;

import jpa.entity.Line;
import jpa.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setup() {
        Station 당산역 = stationRepository.save(Station.builder().name("당산역").build());
        Station line2_잠실역 = stationRepository.save(Station.builder().name("잠실역").build());
        Station line2_시청역 = stationRepository.save(Station.builder().name("시청역").build());

        lineRepository.save(Line.builder()
                .name("2호선")
                .color("green")
                .stations(Arrays.asList(당산역, line2_잠실역, line2_시청역))
                .build());

        Station line9_염창역 = stationRepository.save(Station.builder().name("염창역").build());
        Station line9_등촌역 = stationRepository.save(Station.builder().name("등촌역").build());

        lineRepository.save(Line.builder()
                .name("9호선")
                .color("gold")
                .stations(Arrays.asList(당산역, line9_염창역, line9_등촌역))
                .build());
    }

    @Test
    @DisplayName("line 조회 테스트")
    void findByNameWithLine() {
        // given
        String expected = "9호선";

        // when
        Line actual = lineRepository.findByName(expected);
//        System.out.println(actual.getLineStations().size());

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getLineStations().size()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("line 삭제")
    void delete() {
        // given
        Line line = lineRepository.findByName("9호선");

        // when
        lineRepository.delete(line);

        // then
        Line deleteLine = lineRepository.findByName("9호선");
        assertThat(deleteLine).isNull();
    }

    @Test
    @DisplayName("line 수정")
    void update() {
        // given
        Line line = lineRepository.findByName("9호선");

        // when
        line.changeLine("3호선");

        Line updateLine = lineRepository.findByName("3호선");
        Line deleteLine = lineRepository.findByName("9호선");

        // then
        assertAll(
                () -> assertThat(updateLine.getLineStations().size()).isEqualTo(3),
                () -> assertThat(deleteLine).isNull()
        );
    }

    @Test
    @DisplayName("line 추가")
    void save() {
        // given
        Station station = stationRepository.findByName("잠실역");

        Line expected = Line.builder()
                        .name("8호선")
                        .color("orange")
                        .stations(Collections.singletonList(station))
                        .build();

        // when
        Line actual = lineRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
