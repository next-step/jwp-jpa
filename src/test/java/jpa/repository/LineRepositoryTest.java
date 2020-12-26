package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.domain.StationLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationLineRepository stationLineRepository;

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
    @DisplayName("노선을 조회하는 테스트")
    void findByLine() {
        Line line = lineRepository.save(new Line("4호선"));
        Station station1 = stationRepository.save(new Station("사당역"));
        Station station2 = stationRepository.save(new Station("명동역"));

        StationLine stationLine1 = stationLineRepository.save(new StationLine(station1, line));
        StationLine stationLine2 = stationLineRepository.save(new StationLine(station2, line));

        List<StationLine> stationLines = line.getStationLines();
        assertEquals(stationLines.get(0), stationLine1);
        assertEquals(stationLines.get(1), stationLine2);
    }
}