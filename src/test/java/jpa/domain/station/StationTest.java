package jpa.domain.station;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;

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
        Station station = saveStation("선릉");
        Line line1 = saveLine("YELLOW", "분당선");
        Line line2 = saveLine("GREEN", "2호선");
        line1.addStation(station);
        line2.addStation(station);

        em.flush();
        em.clear();

        // when
        Station findStation = stationRepository.findLineEntityGraphByName("선릉");

        // then
        assertThat(findStation.getLines().size()).isEqualTo(2);
        assertThat(findStation.getLines()).containsExactly(line1, line2)
                .extracting("name").containsExactly("분당선", "2호선");
    }

    private Station saveStation(String name) {
        return stationRepository.save(new Station(name));
    }

    private Line saveLine(String color, String name) {
        return lineRepository.save(new Line(color, name));
    }
}
