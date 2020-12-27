package jpa;

import jpa.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Line expected = new Line("green", "2호선");
        Line actual = lineRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByColor() {
        String expected = "green";
        lineRepository.save(new Line(expected, "2호선"));

        String actual = lineRepository.findByColor(expected).getColor();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByName() {
        String expected = "2호선";
        lineRepository.save(new Line("green", expected));
        String actual = lineRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateColor() {
        Line expected = lineRepository.save(new Line("green", "2호선"));

        expected.changeName("3호선");

        String actual = lineRepository.findByName(expected.getName()).getName();
        assertThat(actual).isEqualTo(expected.getName());
    }

    @Test
    void deleteById() {
        Line expected = lineRepository.save(new Line("green", "2호선"));

        lineRepository.deleteById(expected.getId());
        lineRepository.flush();

        Optional<Line> actual = lineRepository.findById(expected.getId());
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("노선 조회시 속한 지하철 역 보기")
    void find_stations_test() {
        Line line = lineRepository.save(new Line("orange", "3호선"));
        Station station = stationRepository.save(new Station("잠원역"));

        line.addStation(station);

        Line found = lineRepository.findById(line.getId()).get();
        assertThat(found.getStations().size()).isEqualTo(1);
        assertThat(found.getStations().contains(station)).isTrue();
    }

    @Test
    @DisplayName("노선 조회시 속한 지하철 역과 전역의 거리정보까지 보기")
    void find_stations_with_distance_test() {
        Line line = lineRepository.save(new Line("orange", "3호선"));
        Station preStation = stationRepository.save(new Station("잠원 전역"));
        Station saveStation = stationRepository.save(new Station("잠원역", new Distance(preStation.getId(), 10)));

        line.addStation(saveStation);

        Line found = lineRepository.findById(line.getId()).get();
        Station station = found.getStations().get(0);
        assertThat(station.getDistance().getPreStationId()).isEqualTo(preStation.getId());
        assertThat(station.getDistance().getLength()).isEqualTo(10);
    }

    @Test
    @DisplayName("노선 저장시 속한 지하철 역과 전역의 거리정보까지 저장")
    void save_line_with_stations_and_distance_test() {
        Line line = new Line("orange", "3호선");
        Station preStation = stationRepository.save(new Station("잠원 전역"));
        Station saveStation = stationRepository.save(new Station("잠원역", new Distance(preStation.getId(), 10)));
        line.addStation(saveStation);

        Line savedLine = lineRepository.save(line);

        Line found = lineRepository.findById(savedLine.getId()).get();
        Station station = found.getStations().get(0);
        assertThat(station.getDistance().getPreStationId()).isEqualTo(preStation.getId());
        assertThat(station.getDistance().getLength()).isEqualTo(10);
    }
}