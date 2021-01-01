package jpa.repository;

import jpa.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        // given
        Station expected = new Station("잠실역");

        // when
        Station actual = stationRepository.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        // given
        String expected = "잠실역";
        stationRepository.save(new Station(expected));

        // when
        String actual = stationRepository.findByName(expected).getName();

        // then
        assertThat(actual).isEqualTo(expected);

        // 중복된 이름 insert 시 exception 발생 테스트
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            stationRepository.save(new Station(expected));
        });
    }

    @Test
    void identity() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        Station station2 = stationRepository.findById(station1.getId()).orElse(null);
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void identityByName() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        Station station2 = stationRepository.findByName(station1.getName());
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void update() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        station1.setName("강남역");
        Station station2 = stationRepository.findByName("강남역");
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void delete() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        stationRepository.delete(station1);
        Station station2 = stationRepository.findByName("잠실역");
        assertThat(station2).isNull();
    }

}