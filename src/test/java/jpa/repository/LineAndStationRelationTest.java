package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Section;
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
        // JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다.
        line = lineRepository.save(new Line("2호선"));                // 2호선에는 잠실역(환승역), 교대역
        station = stationRepository.save(new Station("잠실역"));
        // 노선에 지하철역 추가
        line.addStation(station);
        //line.addStation(stationRepository.save(new Station("교대역")));
        Section section = new Section(station, stationRepository.save(new Station("교대역")), 3);
        line.addStation(section);
        lineRepository.save(new Line("3호선")).addStation(station);   // 3호선에는 잠실역(환승역)

        // DB에 반영
        //lineRepository.save(line);
        //stationRepository.save(station);
        stationRepository.flush();
        lineRepository.flush();
    }

    @Test
    @DisplayName("환승역을 고려한다. - 다대다 양방향 연관관계 테스트")
    void save() {
        // when
        Line actualLine = lineRepository.save(line);
        Station actualStation = stationRepository.save(station);

        // then
        assertThat(actualLine.getStations()).isEqualTo(line.getStations());
        assertThat(actualStation.getLines()).isEqualTo(station.getLines());
        //actualStation.getLines().forEach(l -> System.out.println(l.getName()));
        //actualLine.getStations().forEach(l -> System.out.println(l.getName()));
    }

    @Test
    void findByNameWithLine() {
        // when
        Station actual = stationRepository.findByName("잠실역");

        // then
        assertThat(actual.getLines().size()).isEqualTo(2);
        assertThat(actual.getLines()).isEqualTo(station.getLines());
        //actual.getLines().forEach(l -> System.out.println(l.getName()));
    }

    @Test
    void findByNameWithStation() {
        // when
        Line actual = lineRepository.findByName("2호선");

        // then
        assertThat(actual.getStations().size()).isEqualTo(2);
        assertThat(actual.getStations()).isEqualTo(line.getStations());
        //actual.getStations().forEach(l -> System.out.println(l.getName()));
    }

    @Test
    void updateWithLine() {
        // when
        Station actual = stationRepository.findByName("교대역");
        actual.setName("신교대역");
        lineRepository.save(new Line("4호선")).addStation(actual);
        stationRepository.flush();

        // then
        assertThat(actual.getName()).isEqualTo("신교대역");
        assertThat(actual.getLines().size()).isEqualTo(2);
        assertThat(actual.getLines()).contains(
                lineRepository.findByName("2호선"),
                lineRepository.findByName("4호선")
        );
    }

    @Test
    void updateWithStation() {
        // when
        Line actual = lineRepository.findByName("2호선");
        actual.setColor("녹색");
        actual.addStation(stationRepository.save(new Station("강남역")));
        lineRepository.flush();

        // then
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
        // given
        Station expected = stationRepository.findByName("잠실역");
        assertThat(expected.getLines().size()).isEqualTo(2);

        // when
        lineRepository.findByName("3호선").removeStation(expected);
        //expected.removeLine(lineRepository.findByName("2호선"));
        Station actual = stationRepository.save(expected);

        // then
        assertThat(actual.getLines().size()).isEqualTo(1);
        assertThat(actual.getLines().stream().findAny().get()).isEqualTo(lineRepository.findByName("2호선"));
    }

    @Test
    void deleteWithStation() {
        // given
        Line expected = lineRepository.findByName("2호선");
        assertThat(expected.getStations().size()).isEqualTo(2);

        // when
        expected.removeStation(stationRepository.findByName("잠실역"));
        Line actual = lineRepository.save(expected);

        // then
        assertThat(actual.getStations().size()).isEqualTo(1);
        assertThat(actual.getStations().stream().findAny().get()).isEqualTo(stationRepository.findByName("교대역"));
    }

}