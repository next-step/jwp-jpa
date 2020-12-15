package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
@DataJpaTest
@DisplayName("Station Repository Test Class")
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void stationSaveTest() {
        Station station = new Station("교대역");
        Station savedStation = stationRepository.save(station);

        assertThat(savedStation.getId()).isNotNull();
        assertThat(savedStation.getName()).isEqualTo(savedStation.getName());
        assertThat(savedStation.getCreatedDate()).isNotNull().isBefore(LocalDateTime.now());
        assertThat(savedStation.getModifiedDate()).isNotNull().isBefore(LocalDateTime.now());
    }

    @Test
    void findByNameTest() {
        String stationName = "교대역";
        Station station = new Station(stationName);
        stationRepository.save(station);

        Station savedStation = stationRepository.findByName(stationName);

        assertThat(savedStation).isEqualTo(station);
    }
}
