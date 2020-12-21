package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stations.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stations.save(new Station(expected));
        String actual = stations.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        Station actual = stations.save(new Station("잠실역"));
        actual.changeName("몽촌토성역");
        Station station2 = stations.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }

    @Test
    void delete() {
        Station actual = stations.save(new Station("잠실역"));
        stations.delete(actual);
        Station station2 = stations.findByName("잠실역");
        assertThat(station2).isNull();
    }
}
