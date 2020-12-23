package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    StationRepository stations;

    @Test
    @DisplayName("역 저장 테스트")
    public void save() throws Exception {
        Station expected = new Station("화정역");
        Station actual = stations.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("역 id로 조회 테스트")
    public void findById() throws Exception {
        Station expected = new Station("화정역");
        stations.save(expected);
        Station byId = stations.findById(expected.getId()).get();

        assertAll(
                () -> assertThat(byId.getId()).isNotNull(),
                () -> assertThat(byId.getId()).isEqualTo(expected.getId()),
                () -> assertThat(byId.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("역 name 으로 조회 테스트")
    public void findByName() throws Exception {
        Station expected = new Station("화정역");
        stations.save(expected);
        Station byName = stations.findByName("화정역");

        assertAll(
                () -> assertThat(byName.getId()).isNotNull(),
                () -> assertThat(byName.getId()).isEqualTo(expected.getId()),
                () -> assertThat(byName.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
    public void findStationWithLine() throws Exception {
        Station savedStation = stations.save(new Station("화정역"));
        Line line = new Line("3호선", "주황");

        savedStation.add(line);

        Station expected = stations.findByName(savedStation.getName());

        assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(savedStation.getName()),
                () -> expected.getLineStations().forEach( l -> assertThat(l.getLine()).isEqualTo(line)),
                () -> expected.getLineStations().forEach( l -> assertThat(l.getLine().getName()).isEqualTo(line.getName())),
                () -> expected.getLineStations().forEach( l -> assertThat(l.getLine().getColor()).isEqualTo(line.getColor()))
        );
    }
}
