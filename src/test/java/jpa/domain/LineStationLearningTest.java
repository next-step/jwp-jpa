package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineStationLearningTest {
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineStationRepository lineStationRepository;

    private Station gangnam;
    private Station seocho;
    private Station bundang;

    @BeforeEach
    void setup() {
        gangnam = new Station("gangnam");
        seocho = new Station("seocho");
        bundang = new Station("bundang");

        stationRepository.save(gangnam);
        stationRepository.save(seocho);
        stationRepository.save(bundang);
    }

    @DisplayName("구간 정보가 포함된 Line을 생성하고 저장할 수 있다.")
    @Test
    void saveTest() {
        String lineColor = "green";
        String lineName = "lineNumberTwo";

        LineStation lineStation = new LineStation(gangnam, seocho, 3L);
        lineStationRepository.save(lineStation);

        Line line = new Line(lineColor, lineName);
        line.addLineStation(lineStation);

        Line saved = lineRepository.save(line);
        assertThat(saved.getStations().size()).isEqualTo(2);
    }

    @DisplayName("구간 정보가 포함된 Line을 저장한 뒤 정보를 조회할 수 있다.")
    @Test
    void getLineTest() {
        String lineColor = "yellow";
        String lineName = "bundangLine";

        LineStation lineStation = new LineStation(seocho, bundang, 5L);

        Line line = new Line(lineColor, lineName);
        line.addLineStation(lineStation);
        lineRepository.save(line);
        lineStationRepository.save(lineStation);

        Line foundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(foundLine).isNotNull();
        assertThat(foundLine.getStations().get(0).getName()).isEqualTo(seocho.getName());

        LineStation foundLineStation = lineStationRepository.findByLine(foundLine).orElse(null);
        assertThat(foundLineStation).isNotNull();
        assertThat(foundLineStation.getLine()).isEqualTo(foundLine);
    }
}
