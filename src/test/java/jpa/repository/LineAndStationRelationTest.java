package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
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

    // 다대일 단방향 연관 관계
    @Test
    void saveWithLine() {
        Station expected = new Station("잠실역");
        // JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다.
        // expected.setLine(new Line("2호선"));   // Not Working!
        expected.setLine(lineRepository.save(new Line("2호선")));
        Station actual = stationRepository.save(expected);
        stationRepository.flush();
    }

    @Test
    void findByNameWithLine() {
        Station actual = stationRepository.findByName("교대역");
        assertThat(actual).isNotNull();
        assertThat(actual.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    void updateWithLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setLine(lineRepository.save(new Line("2호선")));
        // flush를 하지 않을 경우 insert, flush를 할 경우 update
        stationRepository.flush();
        assertThat(expected.getLine().getName()).isEqualTo("2호선");
    }

    @Test
    void removeLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setLine(null);
        stationRepository.flush();
        assertThat(expected.getLine()).isNull();
    }

    // 양방향 연관관계
    @Test
    void findById() {
        Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
        /*
        Station expected = new Station("교대역");
        expected.setLine(line);
        stationRepository.save(expected);
        assertThat(line.getStations()).contains(expected);
         */
    }

    @Test
    void save() {
        Line expected = new Line("2호선");
        //expected.addStation(new Station("잠실역"));
        expected.addStation(stationRepository.save(new Station("잠실")));
        lineRepository.save(expected);
        lineRepository.flush();
    }

    // 연관 관계 편의 메서드 작성 시 주의 사항
    @Test
    void setLine() {
        Station station = new Station("잠실역");
        Line line1 = new Line("2호선");
        Line line2 = new Line("3호선");
        station.setLine(line1);
        station.setLine(line2);
        assertThat(line1.getStations()).doesNotContain(station);
        assertThat(line2.getStations()).contains(station);
    }
}
