package jpa.domain;

import jpa.domain.manyToMany.ManyToManyLine;
import jpa.domain.manyToMany.ManyToManyLineRepository;
import jpa.domain.manyToMany.ManyToManyStation;
import jpa.domain.manyToMany.ManyToManyStationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ManyToManyTest {
    @Autowired
    private ManyToManyStationRepository stationRepository;

    @Autowired
    private ManyToManyLineRepository lineRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("Line에서 새로운 Station을 등록하고 저장할 수 있다.")
    @Test
    void addStationTest() {
        String stationName = "gangnam";
        String lineName = "lineNumber2";
        String lineColor = "green";

        // when
        LINE_SAVED_WITH_STATION(stationName, lineName, lineColor);

        // then
        STATION_EXIST(stationName);
    }

    @DisplayName("Line에서 Station을 조회할 수 있다.")
    @Test
    void getStationAtLineTest() {
        String stationName = "gangnam";
        String lineColor = "green";
        String lineName = "lineNumber2";
        // given
        LINE_SAVED_WITH_STATION(stationName, lineName, lineColor);

        // when
        ManyToManyLine foundLine = lineRepository.findByName(lineName).orElse(null);
        assertThat(foundLine).isNotNull();

        // then
        assertThat(foundLine.getStations().get(0).getName()).contains(stationName);
    }

    @DisplayName("Staiton에서 Line을 조회할 수 있다.")
    @Test
    void getLineAtStationTest() {
        String stationName = "gangnam";
        String lineColor = "green";
        String lineName = "lineNumber2";
        // given
        ManyToManyLine line = LINE_SAVED_WITH_STATION(stationName, lineName, lineColor);

        // when
        ManyToManyStation foundStation = STATION_EXIST(stationName);

        // then
        assertThat(foundStation.getLines()).contains(line);
    }

    @DisplayName("더티 체킹을 통해 영속화 된 Line이 자동으로 업데이트 된다.")
    @Test
    void dirtyCheckTest() {
        String stationName = "uiwang";
        String lineColor = "blue";
        String lineName = "linNumber1";
        int expectedSize = 2;

        // given
        ManyToManyLine line = LINE_SAVED_WITH_STATION(stationName, lineName, lineColor);

        // when
        line.addStation(new ManyToManyStation("sindorim"));

        // then
        ManyToManyLine foundLine = lineRepository.findByName(lineName).orElse(null);
        assertThat(foundLine).isNotNull();
        assertThat(foundLine.getStations()).hasSize(expectedSize);
    }

    @DisplayName("Line을 삭제해도 연관된 Station은 삭제되지 않는다.")
    @Test
    void stationNotDeleteWhenDeleteRelatedLine() {
        String stationName = "sinnonhyeon";
        String lineColor = "gold";
        String lineName = "linNumber9";

        // given
        ManyToManyLine line = LINE_SAVED_WITH_STATION(stationName, lineName, lineColor);
        STATION_EXIST(stationName);

        // when
        lineRepository.deleteById(line.getId());
        entityManager.flush();

        // then
        STATION_EXIST(stationName);
    }

    private ManyToManyLine LINE_SAVED_WITH_STATION(
            final String stationName, final String lineName, final String lineColor
    ) {
        ManyToManyStation station = new ManyToManyStation(stationName);
        ManyToManyLine line = new ManyToManyLine(lineName, lineColor);
        line.addStation(station);

        return lineRepository.save(line);
    }

    private ManyToManyStation STATION_EXIST(final String stationName) {
        ManyToManyStation foundStation = stationRepository.findByName(stationName).orElse(null);
        assertThat(foundStation).isNotNull();

        return foundStation;
    }
}
