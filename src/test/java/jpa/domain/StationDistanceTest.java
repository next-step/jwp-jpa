package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.repository.LineRepository;
import jpa.repository.StationDistanceRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationDistanceTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private StationDistanceRepository stationDistanceRepository;

    @Test
    void save() {
        //given
        Station from1 = new Station("광화문");
        Station from2 = new Station("안국");
        Station current = new Station("종로3가");
        stationRepository.saveAll(Arrays.asList(from1, from2, current));

        //when
        StationDistance stationDistance = new StationDistance(current, from1, 8);
        StationDistance stationDistance2 = new StationDistance(current, from2, 5);
        stationDistanceRepository.saveAll(Arrays.asList(stationDistance, stationDistance2));
        entityManager.flush();
        entityManager.clear();

        //then
        Station actual = stationRepository.findByName("종로3가");
        List<StationDistance> froms = actual.getStationDistances();
        assertThat(froms).hasSize(2);
    }

    @Test
    void saveWithLine() {
        //given
        Line line1 = new Line("1호선", "파랑");
        Line line3 = new Line("3호선", "주황");
        Line line5 = new Line("5호선", "보라");
        lineRepository.saveAll(Arrays.asList(line1, line3, line5));

        Station from1 = new Station("광화문", line5);
        Station from2 = new Station("안국", line3);
        Station current = new Station("종로3가", line1, line3, line5);
        stationRepository.saveAll(Arrays.asList(from1, from2, current));

        //when
        StationDistance stationDistance = new StationDistance(current, from1, 8);
        StationDistance stationDistance2 = new StationDistance(current, from2, 5);
        stationDistanceRepository.saveAll(Arrays.asList(stationDistance, stationDistance2));
        entityManager.flush();
        entityManager.clear();

        //then
        Line actualLine = lineRepository.findByName(line1.getName());
        assertThat(actualLine.getStations()).hasSize(1);
        assertThat(actualLine.getStations().get(0).getStationDistances()).hasSize(2);
    }
}