package jpa.repositories;

import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineStationRepositoryTest {

    @Autowired
    private LineStationRepository lineStationRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Line line7 = Line.of("7호선", "올리브색");
        Line line1 = Line.of("1호선", "파란색");

        Station station_expressBusTerminal = Station.of("고속터미널");
        Station station_banpo = new Station("반포");
        Station station_nowon = new Station("노원");

        Station station_uijeongbu = Station.of("의정부");
        Station station_seoul = Station.of("서울");
        Station station_changdong = Station.of("창동");

        LineStation lineStation1 = LineStation.of(line7, station_expressBusTerminal, 10, station_uijeongbu);
        LineStation lineStation2 = LineStation.of(line1, station_seoul, 15, station_banpo);
        LineStation lineStation3 = LineStation.of(line7, station_nowon, 20, station_changdong);

        lineStationRepository.saveAll(asList(lineStation1, lineStation2, lineStation3));
        entityManager.clear();
    }

    @Test
    @DisplayName("지하철역의 이전역 거리 테스트")
    void stationDistance() {
        LineStation line7_station_expressBusTerminal = lineStationRepository.findByLineNameAndStationName("7호선", "고속터미널");
        assertThat(line7_station_expressBusTerminal.getPreStation().getName()).isEqualTo("의정부");
        assertThat(line7_station_expressBusTerminal.getDistance()).isEqualTo(10);

        LineStation line1_station_seoul = lineStationRepository.findByLineNameAndStationName("1호선", "서울");
        assertThat(line1_station_seoul.getPreStation().getName()).isEqualTo("반포");
        assertThat(line1_station_seoul.getDistance()).isEqualTo(15);

        LineStation line7_station_nowon = lineStationRepository.findByLineNameAndStationName("7호선", "노원");
        assertThat(line7_station_nowon.getPreStation().getName()).isEqualTo("창동");
        assertThat(line7_station_nowon.getDistance()).isEqualTo(20);
    }
}