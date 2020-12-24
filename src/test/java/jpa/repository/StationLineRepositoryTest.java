package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.domain.StationLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StationLineRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationLineRepository stationLineRepository;

    @Test
    void save() {
        Station station = stationRepository.save(new Station("사당역"));
        Line line = lineRepository.save(new Line("2호선"));

        StationLine stationLine = new StationLine(station,line);
        StationLine actual = stationLineRepository.save(stationLine);

        assertEquals(stationLine, actual);
    }


}