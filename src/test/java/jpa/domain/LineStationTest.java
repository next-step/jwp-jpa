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
    private LineStationRepository lineStationRepository;

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
        // given
        String color = "green";
        String name = "lineNumberTwo";
        int distance = 3;
        Line line = 신규_노선_생성됨(color, name, gangnam, seocho, distance);

        // when
        Line foundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(foundLine).isNotNull();

        // then
        assertThat(foundLine.getStations()).contains(gangnam, seocho);
    }

    @DisplayName("역을 조회할 때 어떤 라인에 등록되어 있는지 알 수 있다")
    @Test
    void getLinesByStationTest() {
        // given
        String color = "green";
        String name = "lineNumberTwo";
        int distance = 3;
        Line line = 신규_노선_생성됨(color, name, gangnam, seocho, distance);
        LineStation lineStation = new LineStation(line, gangnam, seocho, 3);
        gangnam.addLineStation(lineStation);

        // when
        Station foundStation = stationRepository.findByName(gangnam.getName()).orElse(null);
        assertThat(foundStation).isNotNull();

        // then
        assertThat(foundStation.getLines()).contains(line);
    }

    @DisplayName("더티 체킹을 통해 LineStation을 업데이트 하고 결과를 조회할 수 있다.")
    @Test
    void updateLineStationTest() {
        // given
        String color = "green";
        String name = "lineNumberTwo";
        int distance = 3;
        Line line = 신규_노선_생성됨(color, name, gangnam, seocho, distance);
        assertThat(line.getStations()).hasSize(2);

        // when
        line.addLineStation(new LineStation(line, seocho, samsung, 5));

        // then
        Line secondFoundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(secondFoundLine).isNotNull();
        assertThat(secondFoundLine.getStations()).hasSize(3);
    }

    @DisplayName("노선의 LineStation을 삭제하면 영속 DB에도 반영된다.")
    @Test
    void cannotDeleteTest() {
        // given
        String color = "green";
        String name = "lineNumberTwo";
        int distance = 3;
        Line line = 신규_노선_생성됨(color, name, gangnam, seocho, distance);
        LineStation lineStation = line.getLineStations().get(0);

        // when
        line.removeLineStation(lineStation);
        entityManager.flush();

        // then
        DB에_연관된_LineStation_없음(line);
        연관된_LineStation_삭제됨(lineStation);
        연관됐던_역들은_삭제되지_않음(gangnam, seocho);
    }

    private Line 신규_노선_생성됨(
            final String color, final String name, final Station upStation, final Station downStation, final int distance
    ) {
        Line line = new Line (color, name);
        line.addLineStation(new LineStation(line, upStation, downStation, distance));
        return lineRepository.save(line);
    }

    private void DB에_연관된_LineStation_없음(final Line line) {
        Line foundLine = lineRepository.findById(line.getId()).orElse(null);
        assertThat(foundLine.getLineStations()).hasSize(0);
    }

    private void 연관된_LineStation_삭제됨(final LineStation lineStation) {
        LineStation foundAfterDelete = lineStationRepository.findById(lineStation.getId()).orElse(null);
        assertThat(foundAfterDelete).isNull();
    }

    private void 연관됐던_역들은_삭제되지_않음(final Station upStation, final Station downStation) {
        Station foundUpStation = stationRepository.findByName(upStation.getName()).orElse(null);
        assertThat(foundUpStation).isNotNull();
        Station foundDownStation = stationRepository.findByName(downStation.getName()).orElse(null);
        assertThat(foundDownStation).isNotNull();
    }
}
