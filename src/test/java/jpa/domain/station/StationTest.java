package jpa.domain.station;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
import jpa.domain.line.LineStation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StationTest {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Station 저장 테스트")
    void stationSaveTest() {
        Station station = saveStation("수서");

        assertThat(station.getId()).isNotNull();
    }

    @Test
    @DisplayName("Station 조회 테스트")
    void stationSelectTest() {
        saveStation("왕십리");
        saveStation("선릉");
        saveStation("모란");
        saveStation("수원");
        saveStation("인천");

        Station findStation1 = stationRepository.findByName("왕십리");
        Station findStation2 = stationRepository.findByName("수원");

        assertThat(findStation1.getName()).isEqualTo("왕십리");
        assertThat(findStation2.getName()).isEqualTo("수원");

        List<Station> result = stationRepository.findAll();

        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("노선 조회 테스트")
    void findLineByStation() {
        // given
        Line line1 = new Line("YELLOW", "분당선");
        Line line2 = new Line("GREEN", "2호선");
        Station station = saveStation("선릉");

        LineStation lineStation1 = LineStation.createLineStation(station, 10);
        LineStation lineStation2 = LineStation.createLineStation(station, 7);

        line1.addLineStation(lineStation1);
        lineRepository.save(line1);
        line2.addLineStation(lineStation2);
        lineRepository.save(line2);

        // when
        em.clear();
        Station findStation = stationRepository.findLineStationsEntityGraphByName("선릉");

        // then
        assertThat(findStation.getLineStations().size()).isEqualTo(2);
        assertThat(findStation.getLineStations()).containsExactly(lineStation1, lineStation2)
                .extracting(l -> l.getLine().getName()).containsExactly("분당선", "2호선");
    }

    private Station saveStation(String name) {
        return stationRepository.save(new Station(name));
    }
}
