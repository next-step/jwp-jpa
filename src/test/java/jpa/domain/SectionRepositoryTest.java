package jpa.domain;

import jpa.common.JpaAuditingDate;
import jpa.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE
        , classes = {JpaAuditingConfig.class, JpaAuditingDate.class}
))
class SectionRepositoryTest {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    LineRepository lineRepository;

    @Autowired
    StationRepository stationRepository;
    
    @Test
    @DisplayName("구간 저장 테스트")
    public void save() {
        // given
        String lineName = "4호선";
        String upwardStationName = "산본역";
        int distance = 10;
        Section section = new Section(this.lineRepository.save(new Line(lineName))
                                    , this.stationRepository.save(new Station(upwardStationName))
                                    , this.stationRepository.save(new Station("수리산역"))
                                    , distance);

        // when
        Section saveSection = this.sectionRepository.save(section);

        // then
        assertAll(
                () -> assertThat(saveSection.getId()).isNotNull()
                , () -> assertThat(saveSection.getLine().getName()).isEqualTo(lineName)
                , () -> assertThat(saveSection.getUpwardStation().getName()).isEqualTo(upwardStationName)
                , () -> assertThat(saveSection.getDownStation()).isNotNull()
                , () -> assertThat(saveSection.getCreatedDate()).isNotNull()
                , () -> assertEquals(saveSection.getDistance(), distance)
        );
    }

    @Test
    @DisplayName("상행역이름으로 구간 조회 테스트")
    public void findByUpwardStation() {
        // given
        String lineName = "4호선";
        String upwardStationName = "산본역";
        String downStationName = "수리산역";
        int distance = 10;
        Station upwardStation = new Station(upwardStationName);
        Station downStation = new Station(downStationName);
        this.sectionRepository.save(new Section(this.lineRepository.save(new Line(lineName))
                , this.stationRepository.save(upwardStation)
                , this.stationRepository.save(downStation)
                , distance));

        // when
        Section findSection = this.sectionRepository.findByUpwardStation(upwardStation).get();

        // then
        assertAll(
                () ->assertThat(findSection).isNotNull()
                , () ->assertEquals(findSection.getUpwardStation(), upwardStation)
                , () ->assertEquals(findSection.getDownStation(), downStation)
                , () ->assertEquals(findSection.getDistance(), distance)
        );
    }

    @Test
    @DisplayName("하행역이름으로 구간 조회 테스트")
    public void findByDownStation() {
        // given
        String lineName = "4호선";
        String upwardStationName = "산본역";
        String downStationName = "수리산역";
        int distance = 10;
        Station upwardStation = new Station(upwardStationName);
        Station downStation = new Station(downStationName);
        this.sectionRepository.save(new Section(this.lineRepository.save(new Line(lineName))
                , this.stationRepository.save(upwardStation)
                , this.stationRepository.save(downStation)
                , distance));

        // when
        Section findSection = this.sectionRepository.findByDownStation(downStation).get();

        // then
        assertAll(
                () ->assertThat(findSection).isNotNull()
                , () ->assertEquals(findSection.getUpwardStation(), upwardStation)
                , () ->assertEquals(findSection.getDownStation(), downStation)
                , () ->assertEquals(findSection.getDistance(), distance)
        );
    }
}