package jpa.domain;

import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository.save(new Station("강남"));
    }

    @Test
    void save() {
        Station station = new Station("잠실역");
        assertThat(station.getId()).isNull();

        Station actual = stationRepository.save(station);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        Station actual = stationRepository.findByName("강남");
        assertThat(actual.getName()).isEqualTo("강남");
    }

    @Test
    void update_name() {
        Station station = stationRepository.findByName("강남");

        station.changeName("역삼");
        Station actual = stationRepository.getOne(station.getId());

        assertThat(actual.getName()).isEqualTo("역삼");
    }

    @Test
    void delete() {
        Station station = stationRepository.findByName("강남");

        stationRepository.delete(station);
        Station actual = stationRepository.findByName("강남");

        assertThat(actual).isNull();
    }
}
