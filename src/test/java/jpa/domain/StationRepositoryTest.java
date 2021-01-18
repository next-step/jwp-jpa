package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private LineRepository lines;
    @Autowired
    private StationRepository stations;

    @BeforeEach
    void setUp() {
        Line blue = lines.save(new Line("1호선", "Blue"));
        Line green = lines.save(new Line("2호선", "Green"));
        Station cityHall = new Station("시청");
        Station seoul = new Station("서울");
        Station chungjungno = new Station("충정로");
        cityHall.addLineStation(blue, seoul,10);
        cityHall.addLineStation(green,chungjungno, 10);
        stations.save(cityHall);
        stations.save(seoul);
        stations.save(chungjungno);
    }

    @Test
    void save() {
        Station expected = new Station("옥수역");
        Station actual = stations.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "옥수역";
        stations.save(new Station(expected));
        String actual = stations.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        Station actual = stations.save(new Station("옥수역"));
        actual.changeName("금호역");
        Station station2 = stations.findByName("금호역");
        assertThat(station2).isNotNull();
    }

    @Test
    void delete() {
        Station actual = stations.save(new Station("옥수역"));
        stations.delete(actual);
        Station station2 = stations.findByName("옥수역");
        assertThat(station2).isNull();
    }

    @Test
    @DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
    void findAndGetLines() {
        //given
        Station cityHall = stations.findByName("시청");

        //when
        List<Line> cityHallLines = cityHall.getLines();
        List<String> collect = cityHallLines.stream()
            .map(Line::getName)
            .collect(Collectors.toList());

        assertThat(collect).contains("2호선");
        assertThat(collect).contains("1호선");
    }
}
