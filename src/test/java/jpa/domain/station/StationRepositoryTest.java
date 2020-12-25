package jpa.domain.station;

import jpa.domain.line.Line;
import jpa.domain.linestation.LineStation;
import jpa.domain.linestation.PreStationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Line line_shin = Line.builder()
                .name("신분당선")
                .color("빨강색")
                .build();

        Line line_2 = Line.builder()
                .name("2호선")
                .color("초록색")
                .build();

        entityManager.persist(line_shin);
        entityManager.persist(line_2);

        Station gangnam = Station.of("강남역");
        Station pangyo = Station.of("판교역");

        LineStation gangnam_lineStation1 = LineStation.builder()
                .line(line_2)
                .station(gangnam)
                .preStationInfo(PreStationInfo.of(pangyo, 10))
                .build();
        gangnam.addLineStation(gangnam_lineStation1);

        LineStation gangnam_lineStation2 = LineStation.builder()
                .line(line_shin)
                .station(gangnam)
                .preStationInfo(PreStationInfo.of(pangyo, 10))
                .build();
        gangnam.addLineStation(gangnam_lineStation2);

        LineStation pangyo_lineStation = LineStation.builder()
                .line(line_shin)
                .station(pangyo)
                .preStationInfo(PreStationInfo.of(null, null))
                .build();
        pangyo.addLineStation(pangyo_lineStation);


        stationRepository.save(gangnam);
        stationRepository.save(pangyo);
    }

    @Test
    @DisplayName("Station 추가 테스트")
    void insert_station_test() {
        // given
        Station expected = Station.of("잠실역");

        // when
        Station actual = stationRepository.save(expected);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Station 전체 조회 테스트")
    void get_all_station_test() {
        // given
        Station expected = Station.of("잠실역");
        stationRepository.save(expected);

        // when
        List<Station> actual = stationRepository.findAll();
        List<String> stationNames = actual.stream()
                .map(Station::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(stationNames.size()).isEqualTo(3),
                () -> assertThat(stationNames).contains("판교역", "잠실역", "강남역")
        );
    }

    @Test
    @DisplayName("Station 조회 테스트")
    void select_station_test() {
        // given
        String expected = "강남역";

        // when
        Station actual = stationRepository.findByName(expected);

        // then
        assertThat(actual.getName()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Station 수정 테스트")
    void update_station_test() {
        // given
        String newStationName = "이대역";

        // when
        Station station = stationRepository.findByName("강남역");
        station.updateName(newStationName);

        Station updatedStation = stationRepository.findByName(newStationName);

        // then
        assertThat(updatedStation.getName()).isEqualTo(newStationName);
    }

    @Test
    @DisplayName("Station 삭제 테스트")
    void delete_station_test() {
        // given
        Station station = stationRepository.findByName("강남역");

        // when
        stationRepository.delete(station);

        // then
        Station deletedStation = stationRepository.findByName("강남역");
        assertThat(deletedStation).isNull();
    }

    @Test
    @DisplayName("지하철역 조회 시 어느 노선에 속하는지 테스트")
    void get_station_included_line_test() {
        // given
        Station station = stationRepository.findByName("강남역");
        List<LineStation> lineStations = station.getLineStations();

        // when
        List<Line> lines = Optional.ofNullable(lineStations)
                .orElse(Collections.emptyList()).stream()
                .map(LineStation::getLine)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(lines).hasSize(2),
                () -> assertThat(lines).contains(
                            Line.builder()
                                .name("2호선")
                                .color("초록색")
                                .build(),
                            Line.builder()
                                .name("신분당선")
                                .color("빨강색")
                                .build())
        );
    }

}
