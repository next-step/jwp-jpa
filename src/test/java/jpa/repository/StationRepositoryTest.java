package jpa.repository;

import jpa.domain.line.Line;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stations;

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
    @DisplayName("노선 조회 시 속한 지하철역을 볼 수 있다.")
    void findStationByLine() {
        Line line3 = new Line("FE5B10", "3호선");
        Line line7 = new Line("54640D", "7호선");
        Line line9 = new Line("AA9872", "9호선");

        Station station = new Station("고속텨미널역");
        station.addLine(line3);
        station.addLine(line7);
        station.addLine(line9);

        Station savedStation = stations.save(station);

        Station findLine = stations.findById(savedStation.getId()).get();
        assertThat(findLine.getLines().size()).isEqualTo(3);
    }
}
