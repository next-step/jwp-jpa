package jpa.domain;

import jpa.domain.Line.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lines;

    @Autowired
    private TestEntityManager em;

    @DisplayName("단건 조회를 확인합니다.")
    @Test
    void findOne() {
        // given
        String lineName = "1호선";
        Line expected = lines.save(new Line(lineName, Color.BLUE));

        // when
        Line actual = lines.findByName(lineName).get();

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
        Optional<Line> lineOptional = lines.findByName("20호선");

        // then
        assertThat(lineOptional.isPresent()).isFalse();
    }

    @DisplayName("여러건 조회를 확인합니다.")
    @Test
    void findAll() {
        // given
        Line line1 = new Line("1호선", Color.BLUE);
        Line line2 = new Line("2호선", Color.GREEN);
        Line line3 = new Line("3호선", Color.RED);
        lines.saveAll(Arrays.asList(line1, line2, line3));

        // when
        List<Line> lineList = lines.findAll();

        // then
        assertAll(
                () -> assertThat(lineList).isNotNull(),
                () -> assertThat(lineList).containsExactly(line1, line2, line3)
        );
    }

    @DisplayName("정상적으로 저장되는지 확인합니다.")
    @Test
    void save() {
        // given
        Line expected = new Line("1호선", Color.BLUE);

        // when
        Line actual = lines.save(expected);

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
        Line savedLine = lines.save(new Line("1호선", Color.BLUE));

        // when
        Line expected = savedLine.changeName("2호선");
        Line actual = lines.findByName(expected.getName()).get();

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("Line.name 의 unique 속성을 확인합니다.")
    @Test
    void unique() {
        // given
        String lineName = "1호선";
        lines.save(new Line(lineName, Color.BLUE));

        // when / then
        assertThrows(DataIntegrityViolationException.class, () -> lines.save(new Line(lineName, Color.RED)));
    }

    @DisplayName("노선 조회 시 속한 지하철역을 볼 수 있다.")
    @Test
    void checkStations() {
        // given
        // 지하철 노선 저장
        String lineName = "2호선";
        Line line1 = new Line(lineName, Color.GREEN);
        em.persist(line1);

        // 지하철역 저장
        Station gyodae = new Station("교대역");
        Station gangnam = new Station("강남역");
        Station hapjeong = new Station("합정역");
        em.persist(gyodae);
        em.persist(gangnam);
        em.persist(hapjeong);

        // 지하철역 노선 저장
        line1.addLineStation(gyodae, gangnam, Distance.ofMeter(100L));
        line1.addLineStation(gyodae, hapjeong, Distance.ofMeter(200L));

        // when
        Line line = lines.findByName(lineName).get();
        List<Station> stations = line.stations();

        // then
        assertAll(
                () -> assertThat(stations).isNotNull(),
                () -> assertThat(stations).contains(gangnam, hapjeong)
        );
    }

    @DisplayName("노선에 역을 추가할 때는 이전 역과 얼마나 차이가 나는지 길이(distance)를 알고 있어야 한다.")
    @Test
    void lineStationDistance() {
        // given
        Line line1 = new Line("1호선", Color.BLUE);

        Station station1 = new Station("관악역");
        Station station2 = new Station("안양역");
        em.persist(station1);
        em.persist(station2);

        Distance distance = Distance.ofMeter(10L);

        line1.addLineStation(station1, station2, distance);

        Line savedLine = lines.save(line1);

        // when
        Line actual = lines.findByName(savedLine.getName()).get();
        LineStation lineStation = actual.getLineStation(station2);

        // then
        assertThat(distance).isEqualTo(lineStation.getDistance());
    }

    @DisplayName("노선에 역을 중복으로 추가 할 수 없다.")
    @Test
    void nonDuplicateLineStation() {
        // given
        Line line1 = new Line("1호선", Color.BLUE);

        Station station1 = new Station("관악역");
        Station station2 = new Station("안양역");
        em.persist(station1);
        em.persist(station2);

        Distance distance = Distance.ofMeter(10L);

        line1.addLineStation(station1, station2, distance);

        Line savedLine = lines.save(line1);

        // when / then
        assertThrows(IllegalArgumentException.class,
                () -> savedLine.addLineStation(station1, station2, Distance.ofMeter(10L)));
    }

    @DisplayName("노선에서 지하철 역을 제거할 수 있다.")
    @Test
    void test() {
        // given
        Line line1 = new Line("1호선", Color.BLUE);

        Station station1 = new Station("관악역");
        Station station2 = new Station("안양역");
        em.persist(station1);
        em.persist(station2);

        line1.addLineStation(station1, station2, Distance.ofMeter(10L));
        Line savedLine = lines.save(line1);

        // when
        savedLine.removeLineStation(station2);

        // then
        assertThat(savedLine.getLineStations().size()).isEqualTo(0);
    }
}
