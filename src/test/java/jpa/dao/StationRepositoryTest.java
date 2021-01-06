package jpa.dao;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
public class StationRepositoryTest {
    @Autowired
    private StationRepository station;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        assertThat(expected.getId()).isNull();
        Station actual = station.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        station.save(new Station(expected));
        String actual = station.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identity() {
        final Station station1 = station.save(new Station("잠실역"));
        final Station station2 = station.findById(station1.getId()).get();
        assertThat(station1 == station2).isTrue();

        final Station station3 = station.findByName("잠실역");
        assertThat(station1 == station3).isTrue();
    }

    @Test
    void update() {
        final Station station1 = station.save(new Station("잠실역"));
        station1.changeName("잠실나루");
        station1.changeName("잠실역");
        station1.changeName("잠실나루");
        final Station station2 = station.findByName("잠실나루");
        assertThat(station2).isNotNull();
    }


}
