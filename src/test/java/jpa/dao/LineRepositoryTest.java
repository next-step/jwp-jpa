package jpa.dao;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    private LineRepository lines;

    @Autowired
    private StationRepository station;

    @Test
    void save() {
        Line expected = new Line("2호선", "green");
        Line actual = lines.save(expected);
        assertThat(actual == expected).isTrue();
    }

    @Test
    void findByName() {
        Line expected1 = new Line("9호선", "gold");
        lines.save(expected1);
        Line expected2 = new Line("2호선", "green");
        lines.save(expected2);
        Line actual1 = lines.findByName("9호선");
        assertThat(actual1.getColor()).isEqualTo("gold");
        Line actual2 = lines.findByName("2호선");
        assertThat(actual2.getColor()).isEqualTo("green");
    }

    @Test
    void remove() {
        Line expected = new Line("9호선", "gold");
        Line actual1 = lines.save(expected);
        assertThat(actual1).isNotNull();
        lines.deleteById(actual1.getId());
        List<Line> actual2 = lines.findAll();
        assertThat(actual2).isEmpty();
    }

    @Test
    @DisplayName("해당 지하철역의 노선 검색")
    void name() {
        final Line line1 = new Line("5호선", "purple");
        final Line line2 = new Line("6호선", "brown");
        final Line line3 = new Line("공항철도", "blue");
        Station station1 = station.save(new Station("공덕역"));
        line1.setStation(station1);
        line2.setStation(station1);
        line3.setStation(station1);
        lines.save(line1);
        lines.save(line2);
        lines.save(line3);
        //assertThat(station1.getLines()).hasSize(3);
    }
}
