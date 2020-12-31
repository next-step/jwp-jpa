package jpa.repository;

import jpa.domain.LineStation;
import jpa.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineStationRepositoryTest {

    @Autowired
    private LineStationRepository lineStationRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void saveWithLineStation() {
        LineStation expected = new LineStation();
        stationRepository.save(new Station("잠실역", expected));
        lineStationRepository.save(expected);
    }

    @Test
    void saveWithLineStation1() {
        LineStation expected = lineStationRepository.save(new LineStation());
        stationRepository.save(new Station("잠실역", expected));
    }


}