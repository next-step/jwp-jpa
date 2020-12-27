package jpa.domain.linestation;

import jpa.domain.line.Line;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineStationRepositoryTest {

    @Autowired
    private LineStationRepository lineStationRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Line line_shin = Line.builder()
                .name("신분당선")
                .color("빨강색")
                .build();

        Line line_2 = Line.builder()
                .name("2호선")
                .color("초록색")
                .build();

        entityManager.persist(line_shin);
        entityManager.persist(line_2);

        Station jamsil = Station.of("잠실역");
        Station gangnam = Station.of("강남역");
        Station pangyo = Station.of("판교역");

        LineStation jamsil_lineStation = LineStation.builder()
                .line(line_2)
                .station(jamsil)
                .preStationInfo(PreStationInfo.of(gangnam, 15))
                .build();
        jamsil.addLineStation(jamsil_lineStation);

        LineStation gangnam_lineStation1 = LineStation.builder()
                .line(line_2)
                .station(gangnam)
                .preStationInfo(PreStationInfo.of(null, null))
                .build();
        gangnam.addLineStation(gangnam_lineStation1);

        LineStation gangnam_lineStation2 = LineStation.builder()
                .line(line_shin)
                .station(gangnam)
                .preStationInfo(PreStationInfo.of(null, null))
                .build();
        gangnam.addLineStation(gangnam_lineStation2);

        LineStation pangyo_lineStation = LineStation.builder()
                .line(line_shin)
                .station(pangyo)
                .preStationInfo(PreStationInfo.of(gangnam, 10))
                .build();
        pangyo.addLineStation(pangyo_lineStation);

        stationRepository.save(jamsil);
        stationRepository.save(gangnam);
        stationRepository.save(pangyo);
    }

    @DisplayName("해당 역의 이전 역 이름과 거리 테스트")
    @Test
    void previous_station_distance_test() {
        LineStation 이호선_잠실역 = lineStationRepository.findByLineNameAndStationName("2호선", "잠실역");
        LineStation 신분당선_판교역 = lineStationRepository.findByLineNameAndStationName("신분당선", "판교역");

        assertThat(이호선_잠실역.getPreStation().getName()).isEqualTo("강남역");
        assertThat(이호선_잠실역.getDistance()).isEqualTo(15);

        assertThat(신분당선_판교역.getPreStation().getName()).isEqualTo("강남역");
        assertThat(신분당선_판교역.getDistance()).isEqualTo(10);
    }
}
