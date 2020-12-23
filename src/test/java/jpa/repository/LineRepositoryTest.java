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
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Line expected = new Line("4호선");
        Line actual = lineRepository.save(expected);
        assertAll(
                ()->assertNotNull(actual.getId()),
                ()->assertEquals(actual.getName(), expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "4호선";
        lineRepository.save(new Line(expected));
        String actual = lineRepository.findByName(expected).getName();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("동일성 보장 기능 테스트")
    void identity() {
        Line line1 = lineRepository.save(new Line("4호선"));
        Line line2 = lineRepository.findById(line1.getId()).get();
        assertTrue(line1 == line2);
    }

    @Test
    void findById() {
        Line line = lineRepository.findByName("3호선");
        assertEquals(line.getStations().size(), 1);
    }

    @Test
    void saveWithStationNoPersistence() {
        Line expected = new Line("2호선");
        expected.addStation(new Station("잠실역"));
        lineRepository.save(expected);
        lineRepository.flush();
    }

    @Test
    void saveWithStationPersistence() {
        Line expeced = new Line("2호선");
        expeced.addStation(stationRepository.save(new Station("잠실역")));
        lineRepository.save(expeced);
        lineRepository.flush();
    }


}