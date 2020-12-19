package jpa.domain;

import jpa.repository.LineRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    void create() {
        Station expected = new Station();
        expected.setName("잠실역");
        Station actual = stationRepository.save(expected);
        stationRepository.flush();

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void update() {
        Station expected = new Station();
        expected.setName("잠실역");
        stationRepository.save(expected);

        expected.setName("교대역");
        Station actual = stationRepository.save(expected);

        stationRepository.flush();
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void delete() {
        Station expected = new Station();
        expected.setName("잠실역");
        Station actual = stationRepository.save(expected);
        assertThat(actual.getId()).isEqualTo(expected.getId());

        stationRepository.deleteById(1L);
        stationRepository.flush();
        assertThat(stationRepository.findAll().size()).isEqualTo(1);
    }
}