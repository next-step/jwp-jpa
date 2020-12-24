package jpa.domain;

import jpa.common.JpaAuditingDate;
import jpa.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE
        , classes = {JpaAuditingConfig.class, JpaAuditingDate.class}
))
public class LineRepositoryTest {

    @Autowired
    LineRepository lineRepository;

    @Test
    @DisplayName("line 저장 테스트")
    public void save() {
        // given
        Line line = new Line("4호선");

        // when
        Line saveLine = this.lineRepository.save(line);

        // then
        assertAll(
                () -> assertThat(saveLine.getId()).isNotNull(),
                () -> assertEquals(saveLine, line),
                () -> assertThat(saveLine.getCreatedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("이름으로 line 조회 테스트")
    public void findByName() {
        // given
        String name = "4호선";
        lineRepository.save(new Line(name));

        // when
        Line lineFindByName = this.lineRepository.findByName(name);

        // then
        assertThat(lineFindByName.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("노선에서 지하철역(Station)들 조회 테스트")
    public void findMappedStation() {
        // given
        String lineName = "4호선";
        String stationName1 = "수리산역";
        String stationName2 = "산본역";
        Line line = new Line(lineName);
        line.addStation(new Station(stationName1));
        line.addStation(new Station(stationName2));
        lineRepository.save(line);

        // when
        Line lineFindByName = this.lineRepository.findByName(lineName);
        List<Station> stations = lineFindByName.getStations();

        // then
        assertAll(
                () -> assertThat(stations.size()).isEqualTo(2)
                , () -> assertThat(stations.toString()).contains(stationName1, stationName2)
        );
    }
}
