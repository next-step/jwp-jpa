package jpa.domain;

import jpa.domain.manyToMany.ManyToManyLine;
import jpa.domain.manyToMany.ManyToManyLineRepository;
import jpa.domain.manyToMany.ManyToManyStation;
import jpa.domain.manyToMany.ManyToManyStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ManyToManyLearningTest {
    @Autowired
    private ManyToManyStationRepository stationRepository;

    @Autowired
    private ManyToManyLineRepository lineRepository;

    private ManyToManyStation station1;
    private ManyToManyStation station2;
    private ManyToManyStation station3;
    private ManyToManyLine line1;
    private ManyToManyLine line2;

    @BeforeEach
    void setup() {
        station1 = new ManyToManyStation("gangnam");
        station2 = new ManyToManyStation("seocho"); // 환승역
        station3 = new ManyToManyStation("bundang");
        line1 = new ManyToManyLine("green", "line2");
        line2 = new ManyToManyLine("yellow", "line bundang");

        stationRepository.save(station1);
        stationRepository.save(station2);
        stationRepository.save(station3);

        line1.addStation(station1);
        line1.addStation(station2);
        lineRepository.save(line1);

        line2.addStation(station2);
        line2.addStation(station3);
        lineRepository.save(line2);
    }

    @DisplayName("라인 조회 시 속한 지하철 역을 볼 수 있다.")
    @Test
    void canGetStationsWhenGetLine() {
        int expectedSize = 2;

        final ManyToManyLine foundLine = lineRepository.findByName(line1.getName()).orElse(null);
        assertThat(foundLine).isNotNull();

        assertThat(foundLine.getStations().size()).isEqualTo(expectedSize);
        assertThat(foundLine.getStations()).contains(station1, station2);
    }

    @DisplayName("지하쳘역 조회 시 어느 노선에 속했는지 볼 수 있다.")
    @Test
    void canGetLineWhenGetStation() {
        ManyToManyStation foundStation2 = stationRepository.findByName(station2.getName()).orElse(null);
        assertThat(foundStation2).isNotNull();

        assertThat(foundStation2.getLines()).contains(line1, line2);
    }

    @DisplayName("노선에 속한 지하철 역을 수정할 수 있다.")
    @Test
    void updateStationInLine() {
        final String koName = "강남";

        final ManyToManyLine foundLine = lineRepository.findByName(line1.getName()).orElse(null);
        assertThat(foundLine).isNotNull();

        foundLine.getStations().get(0).updateStation(new ManyToManyStation(koName));

        assertThat(stationRepository.findByName(koName)).isNotNull();
    }

    @DisplayName("노선에 속한 지하철 역을 삭제해도 노선은 유지된다.")
    @Test
    void deleteStationInLine() {
        ManyToManyLine testTarget = line1;
        int expectedSize = 1;

        assertThat(lineRepository.findById(testTarget.getId())).isNotNull();

        stationRepository.deleteById(testTarget.getStations().get(0).getId());
        ManyToManyLine foundLine = lineRepository.findById(testTarget.getId()).orElse(null);
        assertThat(foundLine).isNotNull();

        // Line에 속한 Station ID를 따로 관리해주지는 않는다.
        // 삭제할 때 주의해서 알아서 잘 삭제해줘야 한다.
        // 혹은 CASCADE 옵션을 잘 조정해야 한다.
        assertThat(foundLine.getStations().size()).isNotEqualTo(expectedSize);
    }
}
