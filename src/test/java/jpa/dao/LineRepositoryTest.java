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
    @DisplayName("노선이 어느 지하철역에 속한지 확인(저장/조회)")
    void searchStationName() {
       Station sataion = station.save(new Station("공덕역"));
       Line line = new Line("5호선", "purple");
       line.setStation(sataion);
       lines.save(line);
       Line actual = lines.findByName("5호선");
       assertThat(actual.getStation().getName()).isEqualTo("공덕역");
    }

    @Test
    @DisplayName("지하철역 수정")
    void updateWithStation() {
        Station sataion = station.save(new Station("공덕역"));
        Line line1 = new Line("5호선", "purple");
        line1.setStation(sataion);
        lines.save(line1);
        Line line2 = lines.findByName("5호선");
        assertThat(line2.getStation()).isNotNull();
        line2.setStation(station.save(new Station("여의도역")));
        lines.flush();
        assertThat(line2.getStation().getName()).isEqualTo("여의도역");
    }

    @Test
    @DisplayName("지하철역 제거")
    void removeWithStation() {
        Station sataion = station.save(new Station("공덕역"));
        Line line1 = new Line("5호선", "purple");
        line1.setStation(sataion);
        lines.save(line1);
        Line line2 = lines.findByName("5호선");
        assertThat(line2.getStation()).isNotNull();
        line2.setStation(null);
        lines.flush();
    }

    @Test
    @DisplayName("지하철 역의 노선조회")
    void findByStationName() {
        Station station1 = station.save(new Station("공덕역"));
        Line line1 = new Line("5호선", "purple");
        line1.setStation(station1);
        lines.save(line1);
        station1.getLines().add(line1);
        Line line2 = new Line("6호선", "brown");
        line2.setStation(station1);
        lines.save(line2);
        station1.getLines().add(line2);
        Station station2 = station.findByName("공덕역");
        assertThat(station2.getLines()).hasSize(2);

    }
}
