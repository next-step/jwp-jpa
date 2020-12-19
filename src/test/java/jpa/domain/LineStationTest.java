package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineStationTest {
    private Station gangnam = new Station("gangnam");
    private Station seocho = new Station("seocho");
    private Station samsung = new Station("samsung");

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        stationRepository.saveAll(Arrays.asList(gangnam, seocho, samsung));
    }

    @DisplayName("역이 있는 상태에서 새로운 라인과 역의 관계를 맺을 수 있다.")
    @Test
    void addLineStationTest() {
        Line line = new Line("green", "lineNumberTwo");

        line.addLineStation(new LineStation(line, gangnam, seocho, 3));

        lineRepository.save(line);
    }

    @DisplayName("새로운 라인을 생성하고 등록된 역 목록을 조회할 수 있다.")
    @Test
    void getStationsAfterCreatingNewLine() {
        Line line = new Line ("green", "lineNumberTwo");
        line.addLineStation(new LineStation(line, gangnam, seocho, 3));
        lineRepository.save(line);

        Line foundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(foundLine).isNotNull();

        assertThat(foundLine.getStations()).contains(gangnam, seocho);
    }

    @DisplayName("역을 조회할 때 어떤 라인에 등록되어 있는지 알 수 있다")
    @Test
    void getLinesByStationTest() {
        Line line = new Line("green", "lineNumberTwo");
        LineStation lineStation = new LineStation(line, gangnam, seocho, 3);
        line.addLineStation(lineStation);
        gangnam.addLineStation(lineStation);

        Station foundStation = stationRepository.findByName(gangnam.getName()).orElse(null);
        assertThat(foundStation).isNotNull();

        assertThat(foundStation.getLines()).contains(line);
    }

    @DisplayName("더티 체킹을 통해 LineStation을 업데이트 하고 결과를 조회할 수 있다.")
    @Test
    void updateLineStationTest() {
        Line line = new Line("green", "lineNumberTwo");
        lineRepository.save(line);

        line.addLineStation(new LineStation(line, gangnam, seocho, 3));
        Line firstFoundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(firstFoundLine).isNotNull();
        assertThat(firstFoundLine.getStations()).hasSize(2);

        line.addLineStation(new LineStation(line, seocho, samsung, 5));
        Line secondFoundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(secondFoundLine).isNotNull();
        assertThat(secondFoundLine.getStations()).hasSize(3);
    }
}
