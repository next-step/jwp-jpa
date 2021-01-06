package jpa.repository;

import jpa.entity.Line;
import jpa.entity.Location;
import jpa.entity.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    void create() {
        // given
        Location expected = Location.builder()
                                .line(Line.builder().name("2호선").color("green").build())
                                .startStation(Station.builder().name("당산역").build())
                                .endStation(Station.builder().name("강남역").build())
                                .distance(10L)
                                .build();

        // when
        Location actual = locationRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void read() {
        // given
        Location expected = Location.builder()
                .line(Line.builder().name("2호선").color("green").build())
                .startStation(Station.builder().name("당산역").build())
                .endStation(Station.builder().name("강남역").build())
                .distance(10L)
                .build();

        // when
        Location actual = locationRepository.save(expected);

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getDistance().getDistance()).isEqualTo(10)
        );
    }
}