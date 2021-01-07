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
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Line expected = new Line("2호선", "green");
        Line actual = lineRepository.save(expected);
        assertThat(actual == expected).isTrue();
    }

    @Test
    void findByName() {
        Line expected1 = new Line("9호선", "gold");
        lineRepository.save(expected1);
        Line expected2 = new Line("2호선", "green");
        lineRepository.save(expected2);
        Line actual1 = lineRepository.findByName("9호선");
        assertThat(actual1.getColor()).isEqualTo("gold");
        Line actual2 = lineRepository.findByName("2호선");
        assertThat(actual2.getColor()).isEqualTo("green");
    }

    @Test
    void remove() {
        Line expected = new Line("9호선", "gold");
        Line actual1 = lineRepository.save(expected);
        assertThat(actual1).isNotNull();
        lineRepository.deleteById(actual1.getId());
        List<Line> actual2 = lineRepository.findAll();
        assertThat(actual2).isEmpty();
    }

    @Test
    @DisplayName("노선이 어느 지하철역에 속한지 확인(저장/조회)")
    void searchStationName() {
       Station sataion = stationRepository.save(new Station("공덕역"));
       Line line = new Line("5호선", "purple");
       line.setStation(sataion);
       lineRepository.save(line);
       Line actual = lineRepository.findByName("5호선");
       assertThat(actual.getStation().getName()).isEqualTo("공덕역");
    }

    @Test
    @DisplayName("지하철역 수정")
    void updateWithStation() {
        Station sataion = stationRepository.save(new Station("공덕역"));
        Line line1 = new Line("5호선", "purple");
        line1.setStation(sataion);
        lineRepository.save(line1);
        Line line2 = lineRepository.findByName("5호선");
        assertThat(line2.getStation()).isNotNull();
        line2.setStation(stationRepository.save(new Station("여의도역")));
        lineRepository.flush();
        assertThat(line2.getStation().getName()).isEqualTo("여의도역");
    }

    @Test
    @DisplayName("지하철역 제거")
    void removeWithStation() {
        Station sataion = stationRepository.save(new Station("공덕역"));
        Line line1 = new Line("5호선", "purple");
        line1.setStation(sataion);
        lineRepository.save(line1);
        Line line2 = lineRepository.findByName("5호선");
        assertThat(line2.getStation()).isNotNull();
        line2.setStation(null);
        lineRepository.flush();
    }

    @Test
    @DisplayName("지하철 역의 노선조회")
    void findByStationName() {
        Station station1 = stationRepository.save(new Station("공덕역"));
        Line line1 = new Line("5호선", "purple");
        line1.setStation(station1);
        lineRepository.save(line1);
        station1.getLines().add(line1);
        Line line2 = new Line("6호선", "brown");
        line2.setStation(station1);
        lineRepository.save(line2);
        station1.getLines().add(line2);
        Station station2 = stationRepository.findByName("공덕역");
        assertThat(station2.getLines()).hasSize(2);

    }
}
