package jpa.domain.line;

import jpa.domain.linestation.LineStation;
import jpa.domain.linestation.PreStationInfo;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        Station gangnam_station = stationRepository.save(Station.of("강남역"));
        Station jamsil_station = stationRepository.save(Station.of("잠실역"));
        Station pangyo_station = stationRepository.save(Station.of("판교역"));

        Line line_2 = Line.builder()
                .name("2호선")
                .color("초록색")
                .build();

        line_2.addLineStation(LineStation.builder()
                .line(line_2)
                .station(jamsil_station)
                .preStationInfo(PreStationInfo.of(gangnam_station, 10))
                .build());

        lineRepository.save(line_2);

        Line line_shin = Line.builder()
                .name("신분당선")
                .color("빨강색")
                .build();

        line_2.addLineStation(LineStation.builder()
                .line(line_2)
                .station(gangnam_station)
                .preStationInfo(PreStationInfo.of(pangyo_station, 5))
                .build());

        lineRepository.save(line_shin);
    }

    @Test
    @DisplayName("Line 추가 테스트")
    void insert_line_test() {
        // given
        Line expected = Line.builder()
                .name("8호선")
                .color("분홍색")
                .build();

        // when
        Line actual = lineRepository.save(expected);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Line 조회 테스트")
    void select_line_test() {
        // given
        String expected = "2호선";

        // when
        Line actual = lineRepository.findByName(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("2호선"),
                () -> assertThat(actual.getColor()).isEqualTo("초록색")
        );
    }

    @Test
    @DisplayName("Line 전체 조회 테스트")
    void select_all_line_test() {
        // given
        Line expected = Line.builder()
                .name("8호선")
                .color("분홍색")
                .build();
        lineRepository.save(expected);

        // when
        List<Line> lines = lineRepository.findAll();
        List<String> lineNames = lines.stream()
                .map(Line::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(lineNames.size()).isEqualTo(3),
                () -> assertThat(lineNames).contains("8호선", "신분당선", "2호선")
        );
    }

    @Test
    @DisplayName("Line 수정 테스트")
    void update_line_test() {
        // given
        String newLineName = "1호선";

        // when
        Line line = lineRepository.findByName("2호선");
        line.updateName(newLineName);

        Line updatedLine = lineRepository.findByName(newLineName);

        // then
        assertAll(
                () -> assertThat(updatedLine.getName()).isEqualTo("1호선"),
                () -> assertThat(updatedLine.getColor()).isEqualTo("초록색")
        );
    }

    @Test
    @DisplayName("Line 삭제 테스트")
    void delete_line_test() {
        // given
        Line line = lineRepository.findByName("2호선");

        // when
        lineRepository.delete(line);

        // then
        Line deletedLine = lineRepository.findByName("2호선");
        assertThat(deletedLine).isNull();
    }


    @Test
    @DisplayName("노선 조회 시 속한 지하철역 조회 테스트")
    void get_line_included_station_test() {
        // given
        Line line = lineRepository.findByName("2호선");
        List<LineStation> lineStations = line.getLineStations();

        // when
        List<Station> stations = Optional.ofNullable(lineStations)
                .orElse(Collections.emptyList()).stream()
                .map(LineStation::getStation)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(stations).hasSize(2),
                () -> assertThat(stations).contains(
                        Station.of("강남역"),
                        Station.of("잠실역"))
        );
    }

}
