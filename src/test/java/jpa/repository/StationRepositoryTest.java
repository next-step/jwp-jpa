package jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;

    @DisplayName("조회 - 노선조회, 환승역 조회")
    @Test
    void line_station_list() {
        //given
        lineRepository.saveAll(Arrays.asList(
              new Line("2호선", "green"),
              new Line("분당선", "yellow")
        ));
        entityManager.flush();
        entityManager.clear();

        Line line2 = lineRepository.findByName("2호선");
        Line lineB = lineRepository.findByName("분당선");

        //when
        Station station1 = new Station("역삼역", line2);
        Station station2 = new Station("선릉역", line2, lineB);
        stationRepository.saveAll(Arrays.asList(station1, station2));
        entityManager.flush();
        entityManager.clear();

        //then
        Station actual = stationRepository.findByName("선릉역");
        assertThat(actual.getLines()).hasSize(2);
        assertThat(actual).isEqualTo(station2);

        Line actualLine2 = lineRepository.findByName("2호선");
        Line actualLineB = lineRepository.findByName("분당선");
        assertThat(actualLine2.getStations()).hasSize(2);
        assertThat(actualLineB.getStations()).hasSize(1);
    }
}