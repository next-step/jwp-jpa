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
public class StationRepositoryTest {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    LineRepository lineRepository;

    @Test
    @DisplayName("station 저장 테스트")
    public void save() {
        // given
        Station station = new Station("산본역");

        // when
        Station saveStation = this.stationRepository.save(station);

        // then
        assertAll(
                () -> assertThat(saveStation.getId()).isNotNull(),
                () -> assertEquals(saveStation, station),
                () -> assertThat(saveStation.getCreatedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("이름으로 station 조회 테스트")
    public void findByName() {
        // given
        String name = "산본역";
        stationRepository.save(new Station(name));

        // when
        Station stationFindByName = this.stationRepository.findByName(name);

        // then
        assertThat(stationFindByName.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("지하철역에서 노선(Line)들 조회 테스트")
    public void findMappedLine() {
        // given
        String stationName = "잠실역";
        String lineName1 = "2호선";
        String lineName2 = "3호선";

        Station station = new Station(stationName);
        this.stationRepository.save(station);

        Line line1 = new Line(lineName1);
        line1.addStation(station);
        Line line2 = new Line(lineName2);
        line2.addStation(station);
        this.lineRepository.save(line1);
        this.lineRepository.save(line2);

        // when
        Station stationByName = this.stationRepository.findByName(stationName);
        List<Line> lines = stationByName.getLines();

        // then
        assertAll(
                () -> assertThat(lines.size()).isEqualTo(2)
                , () -> assertThat(lines.toString()).contains(lineName1, lineName2)
        );
    }
}
