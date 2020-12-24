package jpa.entity;

import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void create() {
        Station expected = new Station("잠실역");
        Station actual = stationRepository.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void saveWithLine() {
        Station expected = new Station("잠실역");

        Line line = new Line("2호선", "green");
        expected.addLine(line);

        Station actual = stationRepository.save(expected);
        assertThat(actual).isEqualTo(expected);

        assertThat(actual.getLines().get(0).getName()).isEqualTo("2호선");
        assertThat(actual.getLines().get(0).getColor()).isEqualTo("green");
    }

    @Test
    void findByName_test() {
        Station station = new Station("잠실역");
        Station expected = stationRepository.save(station);

        Station actual = stationRepository.findByName("잠실역");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update_test() {
        Station actual = stationRepository.findByName("당산역");

        actual.changeStation("잠실역");
        stationRepository.flush();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }
}