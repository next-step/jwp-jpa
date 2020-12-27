package jpa.domain;

import jpa.common.JpaAuditingDate;
import jpa.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Autowired
    FavoriteRepository favoriteRepository;

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


    @ParameterizedTest
    @CsvSource(value = {"산본역,1,0", "잠실역,0,1", "강남역,0,0"})
    @DisplayName("지하철역이 등록된 즐겨찾기 조회 테스트")
    public void findRegistFavorite(String input, String expected1, String expected2) {
        // given
        String stationName1 = "산본역";
        String stationName2 = "잠실역";
        String stationName3 = "강남역";

        Station station1 = new Station(stationName1);
        Station station2 = new Station(stationName2);
        Station station3 = new Station(stationName3);
        this.stationRepository.save(station1);
        this.stationRepository.save(station2);
        this.stationRepository.save(station3);

        Favorite favorite1 = new Favorite(station1, station2);

        // when
        Station findStation = this.stationRepository.findAll().stream()
                                .filter(station -> station.getName().equals(input))
                                .findAny()
                                .get();

        // then
        assertAll(
                () -> assertEquals(findStation.getFavoritesHasDepartureStation().size()
                                    , Integer.parseInt(expected1))
                , () -> assertEquals(findStation.getFavoritesHasArrvalStation().size()
                                    , Integer.parseInt(expected2))
        );
    }
}
