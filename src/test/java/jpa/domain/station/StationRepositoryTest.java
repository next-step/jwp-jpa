package jpa.domain.station;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StationRepositoryTest {
    @Autowired
    private StationRepository stations;


    @Test
    void save() {
        final Station expected = new Station("잠실역");
        final Station actual = stations.save(expected);
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stations.save(new Station(expected));
        final Station actual = stations.findByName(expected);
        assertThat(actual.getName()).isEqualTo(expected);
    }

    @Test
    void identity() {
        Station station1 = stations.save(new Station("잠실역"));

        final Station station3 = stations.findByName("잠실역");
        assertThat(station1 == station3).isTrue();
    }

    @Test
    void update() {
        final Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        final Station station2 = stations.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }
}

