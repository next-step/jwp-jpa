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
    @DisplayName("노선 조회 시 속한 지하철역을 조회하는 기")
    void findAndGetStations() {
        Line line2 = lines.findByName("2호선");
        List<Station> line2Stations = line2.getStations();

        List<String> collect = line2Stations.stream().map(Station::getName).collect(Collectors.toList());
        assertThat(collect).contains("시청");
    }
}
