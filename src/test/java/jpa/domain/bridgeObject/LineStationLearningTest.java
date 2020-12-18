package jpa.domain.bridgeObject;

import jpa.domain.Line;
import jpa.domain.LineRepository;
import jpa.domain.Station;
import jpa.domain.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        Line newLine = createNewLine(lineColor, lineName, gangnam, seocho, 3L);

        assertThat(newLine).isNotNull();
    }

    @DisplayName("구간 정보가 포함된 Line을 저장한 뒤 정보를 조회할 수 있다.")
    @Test
    void getLineTest() {
        String lineColor = "yellow";
        String lineName = "bundangLine";

        createNewLine(lineColor, lineName, seocho, bundang, 5L);

        Line foundLine = lineRepository.findByName(lineName).orElse(null);
        assertThat(foundLine).isNotNull();
        assertThat(foundLine.getStations().get(0).getName()).isEqualTo(seocho.getName());

        LineStation foundLineStation = lineStationRepository.findByLine(foundLine).orElse(null);
        assertThat(foundLineStation).isNotNull();
        assertThat(foundLineStation.getLine()).isEqualTo(foundLine);
    }

    @DisplayName("더티 체킹을 통해 Entity의 변화를 자동으로 DB에 반영할 수 있다.")
    @Test
    void dirtyCheckTest() {
        String lineNumberTwoColor = "green";
        String lineNumberTwoName = "lineNumberTwo";
        String bundangLineColor = "yellow";
        String bundangLineName = "bundangLine";

        createNewLine(lineNumberTwoColor, lineNumberTwoName, gangnam, seocho, 10L);
        createNewLine(bundangLineColor, bundangLineName, seocho, bundang, 7L);

        Line lineNumberTwo = lineRepository.findByName(lineNumberTwoName).orElse(null);
        Line bundangLine = lineRepository.findByName(bundangLineName).orElse(null);
        assertThat(lineNumberTwo).isNotNull();
        assertThat(bundangLine).isNotNull();

        // 업데이트
        LineStation lineStation = bundangLine.getLineStations().get(0);
        assertThat(lineStation.getLine().getId()).isNotEqualTo(lineNumberTwo.getId());
        lineStation.updateLine(lineNumberTwo);

        // 업데이트 이후 (update 구문 실행 확인)
        LineStation foundLineStation = lineStationRepository.findByDistance(lineStation.getDistance())
                .orElse(null);
        assertThat(foundLineStation).isNotNull();
        assertThat(foundLineStation.getLine().getId()).isEqualTo(lineNumberTwo.getId());
    }

    @DisplayName("연관관계가 존재하는 상태로 Line을 삭제할 수 없다.")
    @Test
    void deleteTest() {
        String lineColor = "green";
        String lineName = "lineNumberTwo";

        Line newLine = createNewLine(lineColor, lineName, seocho, gangnam, 10L);
        LineStation lineStation = newLine.getLineStations().get(0);

        LineStation foundLineStation = lineStationRepository.findById(lineStation.getId()).orElse(null);
        assertThat(foundLineStation).isNotNull();

        lineRepository.deleteById(newLine.getId());
        assertThatThrownBy(() -> lineStationRepository.findByDistance(lineStation.getDistance()).orElse(null))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Line createNewLine(
            final String lineColor, final String lineName, final Station upStation,
            final Station downStation, final Long distance
    ) {
        LineStation lineStation = new LineStation(upStation, downStation, distance);
        lineStationRepository.save(lineStation);

        Line line = new Line(lineColor, lineName);
        line.addLineStation(lineStation);

        return lineRepository.save(line);
    }
}
