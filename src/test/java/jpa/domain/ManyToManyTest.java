package jpa.domain;

import jpa.domain.manyToMany.ManyToManyLine;
import jpa.domain.manyToMany.ManyToManyLineRepository;
import jpa.domain.manyToMany.ManyToManyStation;
import jpa.domain.manyToMany.ManyToManyStationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.PATH;

@DataJpaTest
public class ManyToManyTest {
    @Autowired
    private ManyToManyStationRepository stationRepository;

    @Autowired
    private ManyToManyLineRepository lineRepository;

    @DisplayName("Line에서 새로운 Station을 등록하고 저장할 수 있다.")
    @Test
    void addStationTest() {
        // given
        ManyToManyStation station = new ManyToManyStation("gangnam");
        ManyToManyLine line = new ManyToManyLine("green", "lineNumber2");
        line.addStation(station);

        // when
        lineRepository.save(line);

        // then
        ManyToManyStation foundStation = stationRepository.findByName(station.getName()).orElse(null);
        assertThat(foundStation).isNotNull();
    }

    @DisplayName("Line에서 Station을 조회할 수 있다.")
    @Test
    void getStationAtLineTest() {
        String stationName = "gangnam";
        String lineColor = "green";
        String lineName = "lineNumber2";
        // given
        ManyToManyStation station = new ManyToManyStation(stationName);
        ManyToManyLine line = new ManyToManyLine(lineName, lineColor);
        line.addStation(station);
        lineRepository.save(line);

        // when
        ManyToManyLine foundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(foundLine).isNotNull();

        // then
        assertThat(foundLine.getStations()).contains(station);
    }

    @DisplayName("Staiton에서 Line을 조회할 수 있다.")
    @Test
    void getLineAtStationTest() {
        String stationName = "gangnam";
        String lineColor = "green";
        String lineName = "lineNumber2";
        // given
        ManyToManyStation station = new ManyToManyStation(stationName);
        ManyToManyLine line = new ManyToManyLine(lineName, lineColor);
        line.addStation(station);
        lineRepository.save(line);

        // when
        ManyToManyStation foundStation = stationRepository.findByName(stationName).orElse(null);
        assertThat(foundStation).isNotNull();

        // then
        assertThat(foundStation.getLines()).contains(line);
    }
}
