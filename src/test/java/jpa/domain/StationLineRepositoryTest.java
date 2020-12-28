package jpa.domain;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import jpa.domain.stationline.Section;
import jpa.domain.stationline.StationLine;
import jpa.domain.stationline.StationLineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StationLineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;
    
    @Autowired
    private StationRepository stationRepository;
    
    @Autowired
    private StationLineRepository stationLineRepository;

    @BeforeEach
    void setUp() {
        Line line_1 = lineRepository.save(new Line("1호선"));
        Line line_2 = lineRepository.save(new Line("2호선"));

        Station 신도림_station = stationRepository.save(new Station("신도림역"));
        Station 강남_station = stationRepository.save(new Station("강남역"));
        Station 영등포_station = stationRepository.save(new Station("영등포역"));
        Station 사당_station = stationRepository.save(new Station("사당역"));

        stationLineRepository.save(new StationLine(신도림_station, line_1, Section.of(영등포_station.getName(), 10)));
        stationLineRepository.save(new StationLine(신도림_station, line_2, Section.of(사당_station.getName(), 60)));
        stationLineRepository.save(new StationLine(강남_station, line_2, Section.of(사당_station.getName(), 50)));
    }

    @DisplayName("노선 조회 시 속한 지하철 역을 볼 수 있다.")
    @Test
    void check_station() {
        Line line = lineRepository.findByName("2호선");
        List<StationLine> stationLines = line.getStationLines();

        stationLines.forEach(stationLine -> {
            assertThat(stationLine.getLine()).isEqualTo(line);
            assertThat(stationLine.getStation()).isNotNull();
        });
        assertThat(stationLines).hasSize(2);
    }

    @DisplayName("지하철역 조회 시 어느 노선에 속한지 확인할 수 있다. 환승역을 고려하여 여러 노선에 속할 수 있다.")
    @Test
    void check_transfer() {
        Station station = stationRepository.findByName("신도림역");
        List<StationLine> stationLines = station.getStationLines();

        stationLines.forEach(stationLine -> {
            assertThat(stationLine.getLine()).isNotNull();
            assertThat(stationLine.getStation()).isEqualTo(station);
        });
        assertThat(stationLines).hasSize(2);
    }

    @DisplayName("지하철역 조회 시 이전 역과 얼마나 차이가 나는지 길이를 알수 있다.")
    @Test
    void check_distance() {
        Line line = lineRepository.findByName("1호선");
        Station station = stationRepository.findByName("신도림역");
        StationLine stationLine = stationLineRepository.findByStationAndLine(station, line);

        Section resultSection = stationLine.getSection();
        assertThat(resultSection.getPreviousStationName()).isEqualTo("영등포역");
        assertThat(resultSection.getDistance()).isEqualTo(10);
    }
}
