package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lines;

    @Test
    void save() {
        Line expected = new Line("1호선", "Blue");
        Line actual = lines.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
        );
    }

    @Test
    void findByEmail() {
        String expected = "1호선";
        lines.save(new Line("1호선", "Blue"));
        String actual = lines.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        Line actual = lines.save(new Line("1호선", "Blue"));
        actual.changeName("2호선");
        Line line2 = lines.findByName("2호선");
        assertThat(line2).isNotNull();
    }

    @Test
    void delete() {
        Line actual = lines.save(new Line("1호선", "Blue"));
        lines.delete(actual);
        Line line2 = lines.findByName("1호선");
        assertThat(line2).isNull();
    }

    @Test
    @DisplayName("노선 조회 시 속한 지하철역을 조회하는 기능")
    void findAndGetStations() {
        Line cityHall = lines.findByName("2호선");
        List<Station> stations = cityHall.getStations();

        List<String> collect = stations.stream().map(Station::getName).collect(Collectors.toList());
        assertThat(collect).contains("시청");
    }
}
