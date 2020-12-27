package jpa.repository;

import jpa.domain.Line;
import jpa.domain.PreviousStation;
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
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationLineRepository stationLineRepository;

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
    @DisplayName("지하철이 여러 노선을 가지는 기능 테스트")
    void stationToManyLine() {
        PreviousStation previousStation = new PreviousStation(new Station("강남역"), 3);
        Station station1 = stationRepository.save(new Station("사당역"));
        Line line1 = lineRepository.save(new Line("2호선"));
        Line line2 = lineRepository.save(new Line("4호선"));
        StationLine stationLine1 = stationLineRepository.save(new StationLine(previousStation, station1, line1));
        StationLine stationLine2 =  stationLineRepository.save(new StationLine(previousStation, station1, line2));

        List<StationLine> stationLines = station1.getStationLines();
        assertEquals(stationLines.get(0), stationLine1);
        assertEquals(stationLines.get(1), stationLine2);
    }
}