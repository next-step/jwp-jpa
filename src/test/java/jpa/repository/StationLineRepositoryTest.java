package jpa.repository;

import jpa.domain.Line;
import jpa.domain.PreviousStation;
import jpa.domain.Station;
import jpa.domain.StationLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StationLineRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationLineRepository stationLineRepository;

    @Test
    void save() {
        PreviousStation previousStation = new PreviousStation(new Station("강남역"), 3);
        Station station = stationRepository.save(new Station("사당역"));
        Line line = lineRepository.save(new Line("2호선"));

        StationLine stationLine = new StationLine(previousStation, station,line);
        StationLine actual = stationLineRepository.save(stationLine);

        assertEquals(stationLine, actual);
    }

    @Test
    @DisplayName("이전 역 사이의 거리를 조회하는 테스트")
    void distance() {
        PreviousStation previousStation = new PreviousStation(new Station("강남역"), 3);
        Station station = stationRepository.save(new Station("사당역"));
        Line line = lineRepository.save(new Line("2호선"));

        StationLine stationLine = new StationLine(previousStation, station,line);
        StationLine actual = stationLineRepository.save(stationLine);

        StationLine result = stationLineRepository.findByStationAndLine(station,line);

        assertAll(
                ()->assertEquals(actual.getPreviousStation().getPreviousStation(), result.getPreviousStation().getPreviousStation()),
                ()->assertEquals(actual.getPreviousStation().getDistance(), result.getPreviousStation().getDistance())
        );
    }


}