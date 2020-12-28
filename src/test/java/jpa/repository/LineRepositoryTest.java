package jpa.repository;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.station.Station;
import jpa.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    private LineRepository lines;

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Line expected = new Line("2호선");
        Line actual = lines.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "2호선";
        lines.save(new Line(expected));
        String actual = lines.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addRoutes() {
        Line line = lines.save(new Line("2호선"));
        Station fromStation = stations.save(new Station("이대역"));
        Station toStation = stations.save(new Station("신촌역"));
        line.addRoute(fromStation, toStation, 10);

        Line actual = lines.save(line);

        assertThat(actual.getRoutes()).hasSize(2);

        actual.getRoutes()
                .forEach(route ->
                        assertTrue(route.getStation() == fromStation || route.getStation() == toStation));
    }
}
