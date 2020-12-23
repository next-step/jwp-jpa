package jpa.repository;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
import jpa.domain.linestation.LineStation;
import jpa.domain.linestation.LineStationRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineStationTest {
    @Autowired
    LineStationRepository lineStations;

    @Autowired
    StationRepository stations;

    @Autowired
    LineRepository lines;

    private LineStation savedLineStation;

    @BeforeEach
    void setUp() {
        Line line1 = new Line("0D3692", "1호선");
        lines.save(line1);

        LineStation lineStation = new LineStation(new Station("의정부"), new Station("회룡"), 15);
        lineStation.changeLine(line1);
        savedLineStation = lineStations.save(lineStation);
    }

    @Test
    void save() {
        assertAll(
                () -> assertThat(savedLineStation.getId()).isNotNull(),
                () -> assertThat(savedLineStation.getDistance()).isEqualTo(15),
                () -> assertThat(savedLineStation.getStation().getName()).isEqualTo("회룡"),
                () -> assertThat(savedLineStation.getPreStation().getName()).isEqualTo("의정부"),
                () -> assertThat(savedLineStation.getLine().getName()).isEqualTo("1호선")
        );
    }

}
