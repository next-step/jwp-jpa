package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lines;
    @Autowired
    private StationRepository stations;

    @BeforeEach
    void setUp() {
        Line blue = lines.save(new Line("1호선", "Blue"));
        Line green = lines.save(new Line("2호선", "Green"));

        Station cityHall = stations.save(new Station("시청"));
        Station seoul = stations.save(new Station("서울"));
        Station chungjungno = stations.save(new Station("충정로"));

        blue.addLineStation(cityHall, seoul, 10);
        green.addLineStation(cityHall, chungjungno, 10);
        cityHall.addLineStation(blue, seoul, 10);
        cityHall.addLineStation(green, chungjungno, 10);

    }

    @Test
    void save() {
        Line expected = new Line("3호선", "Orange");
        Line actual = lines.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
        );
    }

    @Test
    void findByEmail() {
        String expected = "3호선";
        lines.save(new Line("3호선", "Orange"));
        String actual = lines.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        Line actual = lines.save(new Line("3호선", "Orange"));
        actual.changeName("4호선");
        Line line2 = lines.findByName("4호선");
        assertThat(line2).isNotNull();
    }

    @Test
    void delete() {
        Line actual = lines.save(new Line("3호선", "Orange"));
        lines.delete(actual);
        Line line2 = lines.findByName("3호선");
        assertThat(line2).isNull();
    }

    @Test
    @DisplayName("노선 조회 시 속한 지하철역을 조회하는 기능")
    void findAndGetStations() {
        Line line2 = lines.findByName("2호선");
        List<Station> line2Stations = line2.getStations();

        List<String> collect = line2Stations.stream().map(Station::getName).collect(Collectors.toList());
        assertThat(collect).contains("시청");
    }

    @Test
    @DisplayName("노선에 새로운 역을 추가하는 기능")
    void addNewStation() {
        //given
        Station cityHall = stations.findByName("시청");
        Station newStation = stations.save(new Station("종로3가"));
        Line line1 = lines.findByName("1호선");

        //when
        line1.addLineStation(newStation, cityHall, 10);
        lines.save(line1);

        //then
        Line savedLine1 = lines.findByName("1호선");
        Optional<LineStation> first = savedLine1.getLineStations().stream()
            .filter(lineStation -> lineStation.getStation().equals(newStation))
            .findFirst();

        Assertions.assertThat(first.get().getDistance()).isEqualTo(10);


    }
}
