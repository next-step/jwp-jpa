package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineStationTest {
    private Station gangnam = new Station("gangnam");
    private Station seocho = new Station("seocho");

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void setup() {
        stationRepository.saveAll(Arrays.asList(gangnam, seocho));
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
}
