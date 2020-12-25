package jpa;

import jpa.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;

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
    void update() {
        Station expected = new Station("잠실역");
        Station saved = stationRepository.save(expected);

        saved.changeName("교대역");

        Station actual = stationRepository.findByName("교대역");
        assertThat(actual.getName()).isEqualTo("교대역");
    }

    @Test
    void deleteById() {
        Station station = new Station("교대역");
        Station saved = stationRepository.save(station);

        stationRepository.deleteById(saved.getId());
        stationRepository.flush();

        Optional<Station> found = stationRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stationRepository.save(new Station(expected));

        String actual = stationRepository.findByName(expected).getName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
    void findWithLine_test() {
        Station station = new Station("교대");
        Line line1 = new Line("2호선");
        Line line2 = new Line("3호선");

        stationRepository.save(station);
        lineRepository.save(line1);
        lineRepository.save(line2);

        station.addLine(line1);
        station.addLine(line2);

        Station found = stationRepository.findById(station.getId()).get();
        assertThat(found.getName()).isEqualTo("교대");
        assertThat(found.getLines().contains(line1)).isTrue();
        assertThat(found.getLines().contains(line2)).isTrue();
    }
}