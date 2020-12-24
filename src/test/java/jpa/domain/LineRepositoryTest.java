package jpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    private LineRepository lines;

    @Autowired
    private StationRepository stations;

    @Test
    void findById() {
        final Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void save() {
        Line expected = new Line("2호선");
        // Line은 연관관계의 주인이 아니기 때문에, 외래키 값에 영향을 주지 않는다. (update 쿼리가 발생하지 않는다.)
        expected.addStation(stations.save(new Station("잠실역")));
        lines.save(expected);
        lines.flush(); // transaction commit
    }
}
