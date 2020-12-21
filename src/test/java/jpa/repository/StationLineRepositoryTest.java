package jpa.repository;

import jpa.entity.Line;
import jpa.entity.Station;
import jpa.entity.StationLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StationLineRepositoryTest {

    @Autowired
    private StationLineRepository stationLineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;

    @Test
    @DisplayName("노선조회")
    void selectLine() {
        Line line = lineRepository.findByColor("green");
        List<StationLine> stationLineList = stationLineRepository.findByLine(line);
        assertThat(stationLineList.get(0).getStation()).isEqualTo(stationRepository.findByName("신도림역"));
    }

    @Test
    @DisplayName("역조회")
    void searchStation() {
        Station station = stationRepository.findByName("신도림역");
        List<StationLine> stationLineList = stationLineRepository.findByStation(station);
        assertThat(stationLineList).hasSize(2);
    }
}
