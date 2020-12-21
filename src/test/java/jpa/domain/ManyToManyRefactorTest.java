package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ManyToManyRefactorTest {
    private Station gangnam;
    private Station jamsil;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        gangnam  = new Station("강남");
        jamsil = new Station("잠실");

        stationRepository.saveAll(Arrays.asList(gangnam, jamsil));
    }

    @DisplayName("생성된 라인에 새로운 거리를 추가할 수 있다.")
    @Test
    void addNewDistance() {
        String color = "녹색";
        String name = "2호선";
        int distanceValue = 5;

        // given
        Line line = new Line(color, name);
        Distance distance = new Distance(distanceValue, new Section(gangnam, jamsil));

        // when
        line.addDistance(distance);
        lineRepository.save(line);
        entityManager.flush();

        // then
        Distance foundDistance = distanceRepository.findById(distance.getId()).orElse(null);
        assertThat(foundDistance).isNotNull();
    }
}
