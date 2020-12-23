package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
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
                ()->assertNotNull(actual.getId()),
                ()->assertEquals(actual.getName(), expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stationRepository.save(new Station(expected));
        String actual = stationRepository.findByName(expected).getName();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("동일성 보장 기능 테스트")
    void identity() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        Station station2 = stationRepository.findById(station1.getId()).get();
        assertTrue(station1 == station2);
    }

    @Test
    void saveWithLine() {
        Station expected = new Station("잠실역");
        expected.setLine(lineRepository.save(new Line("2호선")));
        Station actual = stationRepository.save(expected);
        stationRepository.flush();
    }

    @Test
    void findByNameWithLine() {
        Station actual = stationRepository.findByName("교대역");
        assertNotNull(actual);
        assertEquals(actual.getLine().getName(), "3호선");
    }

    @Test
    void updateWithLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setLine(lineRepository.save(new Line("2호선")));
        stationRepository.flush();
    }
}