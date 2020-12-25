package jpa.entity;

import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void create() {
        Line expected = new Line("3호선", "orange");
        expected.addStation(stationRepository.save(new Station("잠실역")));

        Line actual = lineRepository.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByNameWithStation() {
        Station actual = stationRepository.findByName("당산역");
        Line line = new Line("2호선", "green");
        actual.addLine(line);

        assertThat(actual.getLines().get(0).getName()).isEqualTo("2호선");
    }

    @Test
    void updateWithStation() {
        Line actual = lineRepository.findByName("2호선");
        actual.changeStation("잠실역");

        lineRepository.flush();
        assertThat(actual).isNotNull();
    }

    @Test
    void removeStation() {
        // null로 변경했는데 역의 이름은 아직 당산역으로 되어있다.
        Line expected = lineRepository.findByName("2호선");

        expected.changeStation(null);
        lineRepository.flush();
    }
}
