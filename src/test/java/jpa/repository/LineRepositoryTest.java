package jpa.repository;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
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
class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Line expected = new Line("군청색","1호선");
        Line actual = lineRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
        );
    }

    @Test
    void findByName() {
        String expected = "1호선";
        lineRepository.save(new Line("군청색", expected));
        String actual = lineRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("노선 조회 시 속한 지하철역을 볼 수 있다.")
    void findStationByLine() {
        Station station1 = new Station("의정부역");
        Station station2 = new Station("회룡역");
        Station station3 = new Station("망월사역");
        stationRepository.saveAll(Arrays.asList(station1, station2, station3));

        Line line1 = new Line("0D3692", "1호선");
        line1.addStation(station1);
        line1.addStation(station2);
        line1.addStation(station3);
        Line savedLine = lineRepository.save(line1);

        Line findLine = lineRepository.findById(savedLine.getId()).get();
        assertThat(findLine.getStations().size()).isEqualTo(3);
    }
}
