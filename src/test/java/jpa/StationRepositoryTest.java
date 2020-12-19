package jpa;

import jpa.domain.Station;
import jpa.domain.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository repository;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = repository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        repository.save(new Station(expected));
        String actual = repository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }
}