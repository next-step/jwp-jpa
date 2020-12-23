package jpa.repository;

import jpa.domain.linestation.LineStation;
import jpa.domain.linestation.LineStationRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stations;

    @Autowired
    private LineStationRepository lineStations;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stations.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stations.save(new Station(expected));
        String actual = stations.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    @DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
    void findStationByLine() {

        Station station = new Station("고속터미널");
        Station station3 = new Station("잠원");
        Station station7 = new Station("반포");
        Station station9 = new Station("사평");

        LineStation lineStation3 = new LineStation(station3, station, 5);
        LineStation lineStation7 = new LineStation(station7, station,6);
        LineStation lineStation9 = new LineStation(station9, station,7);
        lineStations.saveAll(Arrays.asList(lineStation3, lineStation7, lineStation9));

        Station findStation = stations.findById(station.getId()).get();
        assertAll(
                () -> assertThat(findStation.getName()).isEqualTo("고속터미널"),
                () -> assertThat(findStation.getLineStations().size()).isEqualTo(3)
        );
    }
}
