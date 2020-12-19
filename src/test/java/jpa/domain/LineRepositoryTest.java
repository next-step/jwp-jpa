package jpa.domain;

import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    void findByNameWithLine() {
        Station actual = stationRepository.findByName("교대역");
        assertThat(actual).isNotNull();
        assertThat(actual.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    void updateWithLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setName("교대역");
        expected.setLine(lineRepository.save(new Line().setName("2호선")));
        stationRepository.flush();

        assertThat(expected.getLine().getName()).isEqualTo("2호선");
    }

    @Test
    void removeLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setLine(null);
        stationRepository.flush();

        assertThat(expected.getLine()).isNull();
    }

    @Test
    void findById() {
        Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void save() {
        Line expected = new Line();
        List<Station> stationList = new ArrayList<>();
        stationList.add(new Station().setName("잠실역"));
        expected.setName("2호선").setStations(stationList);

        lineRepository.save(expected);
        lineRepository.flush();

        assertThat(expected.getName()).isEqualTo("2호선");
        assertThat(expected.getStations().get(0).getName()).isEqualTo("잠실역");
    }
}
