package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineAndStationRelationTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    private Line line;
    private Station station;

    @BeforeEach
    void init() {
        line = lineRepository.save(new Line("2호선"));            // 2호선에는 잠실역, 교대역
        station = stationRepository.save(new Station("잠실역"));   // 잠실역은 2호선, 3호선
        // JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다.
        station.addLine(line);
        station.addLine(lineRepository.save(new Line("3호선")));
        //line.addStation(station);
        line.addStation(stationRepository.save(new Station("교대역")));

        //lineRepository.save(line);
        //stationRepository.save(station);
        stationRepository.flush();
        lineRepository.flush();
    }

    @Test
    @DisplayName("환승역을 고려한다. - 다대다 양방향 연관관계 테스트")
    void save() {
        Line actualLine = lineRepository.save(line);
        Station actualStation = stationRepository.save(station);

        assertThat(actualLine.getStations()).isEqualTo(line.getStations());
        assertThat(actualStation.getLines()).isEqualTo(station.getLines());
    }

    @Test
    void findByNameWithLine() {
        Station actual = stationRepository.findByName("잠실역");

        assertThat(actual.getLines().size()).isEqualTo(2);
        assertThat(actual.getLines()).isEqualTo(station.getLines());
        //actual.getLines().forEach(l -> System.out.println(l.getName()));
    }

    @Test
    void findByNameWithStation() {
        Line actual = lineRepository.findByName("2호선");

        assertThat(actual.getStations().size()).isEqualTo(2);
        assertThat(actual.getStations()).isEqualTo(line.getStations());
        //actual.getStations().forEach(l -> System.out.println(l.getName()));
    }

    @Test
    void updateWithLine() {
        Station actual = stationRepository.findByName("교대역");
        actual.setName("신교대역");
        actual.addLine(lineRepository.save(new Line("4호선")));
        stationRepository.flush();
        assertThat(actual.getName()).isEqualTo("신교대역");
        assertThat(actual.getLines().size()).isEqualTo(2);
        assertThat(actual.getLines()).contains(
                lineRepository.findByName("2호선"),
                lineRepository.findByName("4호선")
        );
    }

    @Test
    void updateWithStation() {
        Line actual = lineRepository.findByName("2호선");
        actual.setColor("녹색");
        actual.addStation(stationRepository.save(new Station("강남역")));
        lineRepository.flush();
        assertThat(actual.getColor()).isEqualTo("녹색");
        assertThat(actual.getStations().size()).isEqualTo(3);
        assertThat(actual.getStations()).contains(
                stationRepository.findByName("잠실역"),
                stationRepository.findByName("교대역"),
                stationRepository.findByName("강남역")
        );
    }

    @Test
    void deleteWithLine() {
        Station expected = stationRepository.findByName("교대역");
        assertThat(expected.getLines().size()).isEqualTo(1);

        expected.removeLine(lineRepository.findByName("2호선"));
        Station actual = stationRepository.save(expected);
        assertThat(actual.getLines().size()).isEqualTo(0);
    }

    @Test
    void deleteWithStation() {
        Line expected = lineRepository.findByName("2호선");
        assertThat(expected.getStations().size()).isEqualTo(2);

        expected.removeStation(stationRepository.findByName("잠실역"));
        Line actual = lineRepository.save(expected);
        assertThat(actual.getStations().size()).isEqualTo(1);
        assertThat(actual.getStations().stream().findAny().get()).isEqualTo(stationRepository.findByName("교대역"));
    }

}
