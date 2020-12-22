package jpa.domain.line;

import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @PersistenceContext
    private EntityManager em;

    private Line saveLine(String color, String name) {
        return lineRepository.save(new Line(color, name));
    }

    private Station saveStation(String name) {
        return stationRepository.save(new Station(name));
    }

    @Test
    @DisplayName("Line 저장 테스트")
    void lineSaveTest() {
        Line savedLine1 = saveLine("YELLOW", "분당선");
        Line savedLine2 = saveLine("YELLOW", "수인선");

        assertThat(savedLine1.getId()).isNotNull();
        assertThat(savedLine2.getId()).isNotNull();
    }

    @Test
    @DisplayName("Line 조회 테스트")
    void lineSelectTest() {
        saveLine("YELLOW", "분당선");
        saveLine("YELLOW", "수인선");

        Line findLine1 = lineRepository.findByName("분당선");
        Line findLine2 = lineRepository.findByName("수인선");

        assertThat(findLine1.getName()).isEqualTo("분당선");
        assertThat(findLine2.getName()).isEqualTo("수인선");

        Line findLine3 = lineRepository.findByNameAndColor("분당선", "YELLOW");
        Line findLine4 = lineRepository.findByNameAndColor("수인선", "YELLOW");

        assertThat(findLine3).extracting("name", "color").containsExactly("분당선", "YELLOW");
        assertThat(findLine4).extracting("name", "color").containsExactly("수인선", "YELLOW");

        List<Line> result = lineRepository.findByColor("YELLOW");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("지하철역 조회 테스트")
    void findStationByLine() {
        // given
        Line line = saveLine("YELLOW", "분당선");
        Station station1 = saveStation("모란");
        Station station2 = saveStation("죽전");
        line.addStation(station1);
        line.addStation(station2);

        em.flush();
        em.clear();

        // when
        Line findLine = lineRepository.findByName("분당선");

        // then
        assertThat(findLine.getStations().size()).isEqualTo(2);
        assertThat(findLine.getStations()).containsExactly(station1, station2)
                .extracting("name")
                .containsExactly("모란", "죽전");
    }
}
