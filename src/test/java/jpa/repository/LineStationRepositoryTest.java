package jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineStationRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private LineStationRepository lineStationRepository;

    @BeforeEach
    void setUp() {
        //given
        Line line1 = new Line("1호선");
        Line line3 = new Line("3호선");
        Line line5 = new Line("5호선");
        lineRepository.saveAll(Arrays.asList(line1, line3, line5));

        //1호선
        Station stationJong2 = new Station("종각");
        Station stationJong5 = new Station("종로5가");
        //3호선
        Station stationAn = new Station("안국역");
        Station stationEl3 = new Station("을지로3가");
        //5호선
        Station stationGwang = new Station("광화문");
        Station stationEl4 = new Station("을지로4가");
        //환승역
        Station stationJong3 = new Station("종로3가");

        stationRepository
              .saveAll(
                    Arrays.asList(stationJong2, stationJong3, stationJong5, stationAn, stationEl3,
                          stationGwang, stationEl4));

        LineStation line1StationJong2 = new LineStation(line1, stationJong2, stationJong3, 1);
        LineStation line1StationJong3 = new LineStation(line1, stationJong3, stationJong5, 1);

        LineStation line3StationJong3 = new LineStation(line3, stationJong3, stationAn, 3);
        LineStation line3StationEl3 = new LineStation(line3, stationEl3, stationJong3, 3);

        LineStation line5StationJong3 = new LineStation(line5, stationJong3, stationGwang, 8);
        LineStation line5StationEl4 = new LineStation(line5, stationEl4, stationJong3, 8);

        lineStationRepository.saveAll(
              Arrays.asList(line1StationJong2, line1StationJong3, line3StationJong3,
                    line3StationEl3, line5StationJong3, line5StationEl4));
        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("노선조회 - 지하철역 목록")
    @Test
    void line() {
        //then
        Line line = lineRepository.findByName("1호선");
        List<LineStation> actual = lineStationRepository.findByLine(line);
        List<LineStation> lineStations = line.getLineStations();

        assertThat(lineStations.size()).isEqualTo(actual.size());
        assertThat(lineStations.containsAll(actual)).isTrue();
    }

    @DisplayName("지하철역 조회 - 환승역")
    @Test
    void station() {
        //then
        Station station = stationRepository.findByName("종로3가");
        List<LineStation> lineStations = lineStationRepository.findByStation(station);

        assertThat(station.getLineStations().size()).isEqualTo(lineStations.size());
        assertThat(station.getLineStations().containsAll(lineStations)).isTrue();
    }
}