package jpa.domain;

import jpa.domain.Line.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Autowired
    private EntityManager em;

    @DisplayName("단건 조회를 확인합니다.")
    @Test
    void findOne() {
        // given
        String stationName = "서울역";
        Station expected = stations.save(new Station(stationName));

        // when
        Station actual = stations.findByName(stationName).get();

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("조회 시 값이 없을 경우 null 이 반환됩니다.")
    @Test
    void findOneNull() {
        // when
        Optional<Station> stationOptional = stations.findByName("당정역");

        // then
        assertThat(stationOptional.isPresent()).isFalse();
    }

    @DisplayName("여러건 조회를 확인합니다.")
    @Test
    void findAll() {
        // given
        Station station1 = new Station("서울역");
        Station station2 = new Station("잠실역");
        Station station3 = new Station("합정역");
        stations.saveAll(Arrays.asList(station1, station2, station3));

        // when
        List<Station> stationList = stations.findAll();

        // then
        assertAll(
                () -> assertThat(stationList).isNotNull(),
                () -> assertThat(stationList).containsExactly(station1, station2, station3)
        );
    }

    @DisplayName("정상적으로 저장되는지 확인합니다.")
    @Test
    void save() {
        // given
        Station expected = new Station("서울역");

        // when
        Station actual = stations.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(actual).isSameAs(expected)
        );
    }

    @DisplayName("변경 감지가 정상적으로 작동하는지 확인합니다.")
    @Test
    void update() {
        // given
        Station savedStation = stations.save(new Station("서울역"));

        // when
        Station expected = savedStation.changeName("잠실역");
        Station actual = stations.findByName(expected.getName()).get();

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("Station.name 의 unique 속성을 확인합니다.")
    @Test
    void unique() {
        // given
        String stationName = "서울역";
        stations.save(new Station(stationName));

        // when / then
        assertThrows(DataIntegrityViolationException.class, () -> stations.save(new Station(stationName)));
    }

    @DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
    @Test
    @Commit
    void checkLines() {
        // given
        // 지하철 노선 저장
        Line line1 = new Line("1호선", Color.BLUE);
        Line line4 = new Line("4호선", Color.GREEN);
        em.persist(line1);
        em.persist(line4);

        // 지하철역 저장
        String stationName = "금정역";
        Station geumjeong = new Station(stationName);
        em.persist(geumjeong);

        // 지하철역 노선 저장
        em.persist(new StationLine(geumjeong, line1));
        em.persist(new StationLine(geumjeong, line4));

        // when
        Station station = stations.findByName(stationName).get();
        List<Line> lines = station.lines();

        // then
        assertAll(
                () -> assertThat(lines).isNotNull(),
                () -> assertThat(lines).contains(line1, line4)
        );
    }
}
