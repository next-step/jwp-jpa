package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Autowired
    private LineRepository lines;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stations.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";

        stations.save(new Station(expected));
        String actual = stations.findByName(expected).getName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("동일성 보장 실습")
    void identity() {
        Station station1 = stations.save(new Station("잠실역"));
        Station station2 = stations.findById(station1.getId()).get();
        Station station3 = stations.findByName("잠실역");
        assertThat(station1 == station2).isTrue();
        assertThat(station1 == station3).isTrue();
    }

    @Test
    @DisplayName("변경 감지 실습")
    void update() {
        Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        // 영속성 컨텍스트에서 name으로는 엔티티 클래스를 찾을 수 없다. (id가 식별자 이기 때문에)
        // 그렇기에 findByName은 영속성 컨텍스트를 거치지 않고 바로 DB를 접근한다. (id 기반으로 조회하지 않는 경우 (JPQL))
        // 이 전에 flush가 수행되어 "몽촌토성역"이 DB에 반영이 된다. (변경 감지를 통해 반영한다.)
        Station station2 = stations.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }

    @Test
    @DisplayName("연관관계 설정 실습")
    void saveWithLine() {
        // 관계를 맺는 엔티티(Line) 역시 영속성 관리가 되어야 한다.
        final Line line = lines.save(new Line("2호선"));

        final Station station = new Station("잠실역");
        station.setLine(line);
        final Station actual = stations.save(station);

        final Station station2 = stations.findByName("잠실역");

        assertThat(actual.getLine().getName()).isEqualTo("2호선");
        assertThat(station2.getLine().getName()).isEqualTo("2호선");
    }

    @Test
    @DisplayName("data.sql 과 함께 연관관계 실습")
    void findByNameWithLine() {
        Station actual = stations.findByName("교대역");

        assertThat(actual).isNotNull();
        assertThat(actual.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    void updateWithLine() {
        Station expected = stations.findByName("교대역");
        expected.setLine(lines.save(new Line("2호선")));
        stations.flush(); // transaction commit
    }
}
