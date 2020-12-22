package jpa.repository;

import jpa.entity.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;
    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stationRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stationRepository.save(new Station(expected));
        String actual = stationRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identity() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        Station station2 = stationRepository.findById(station1.getId()).get();
        assertThat(station1 == station2).isTrue();

        Station station3 = stationRepository.findByName("잠실역");
        assertThat(station1 == station3).isTrue();
    }

}
