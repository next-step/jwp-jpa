package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ManyToManyRefactorTest {
    private Station gangnam;
    private Station jamsil;
    private Station samsung;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        gangnam  = new Station("강남");
        jamsil = new Station("잠실");
        samsung = new Station("삼성");

        stationRepository.saveAll(Arrays.asList(gangnam, jamsil, samsung));
    }

    @DisplayName("생성된 라인에 새로운 거리를 추가할 수 있다.")
    @Test
    void addNewDistance() {
        String color = "녹색";
        String name = "2호선";
        int distanceValue = 5;
        int expectedSize = 1;

        Line line = LINE_CREATED(color, name, distanceValue, gangnam, jamsil);

        // then
        Line foundLine = LINE_FOUND(line);
        assertThat(foundLine.getDistances()).hasSize(expectedSize);
        assertThat(foundLine.getStations()).contains(gangnam, jamsil);
    }

    @DisplayName("생성된 라인에 속한 역들을 볼 수 있다.")
    @Test
    void getStationsFromLineTest() {
        String color = "녹색";
        String name = "2호선";
        int distanceValue = 5;

        // given
        Line line = LINE_CREATED(color, name, distanceValue, gangnam, jamsil);

        // when
        List<Station> stations = line.getStations();

        // then
        assertThat(stations).contains(gangnam, jamsil);
    }

    @DisplayName("라인을 변경 시 더티 체킹을 통한 자동 업데이트가 된다.")
    @Test
    void dirtyCheckTest() {
        String color = "녹색";
        String name = "2호선";
        int distanceValue = 5;
        int expectedSize = 3;

        // given
        Line line = LINE_CREATED(color, name, distanceValue, gangnam, jamsil);

        // when
        line.addDistance(new Distance(distanceValue, new Section(samsung, jamsil)));
        entityManager.flush();

        // then
        Line foundLine = LINE_FOUND(line);
        assertThat(foundLine.getStations()).hasSize(expectedSize);
    }

    @DisplayName("라인을 삭제해도 연관된 역은 사라지지 않는다.")
    @Test
    void deleteTest() {
        String color = "녹색";
        String name = "2호선";
        int distanceValue = 5;
        int expectedSize = 2;

        // given
        Line line = LINE_CREATED(color, name, distanceValue, gangnam, jamsil);
        Long distanceId = line.getDistances().get(0).getId();

        // when
        lineRepository.deleteById(line.getId());
        entityManager.flush();

        // then
        LINE_DELETED(line);
        STATIONS_NOT_DELETED(gangnam, jamsil, expectedSize);
        DISTANCE_DELETED(distanceId);
    }

    public Line LINE_CREATED(
            final String color, final String name, final int distanceValue,
            final Station upStation, final Station downStation
    ) {
        Line line = new Line(color, name);
        Distance distance = new Distance(distanceValue, new Section(upStation, downStation));
        line.addDistance(distance);
        Line save = lineRepository.save(line);
        entityManager.flush();

        return save;
    }

    public Line LINE_FOUND(final Line line) {
        Line foundLine = lineRepository.findById(line.getId()).orElse(null);
        assertThat(foundLine).isNotNull();

        return foundLine;
    }

    public void LINE_DELETED(final Line line) {
        Line foundLine = lineRepository.findById(line.getId()).orElse(null);
        assertThat(foundLine).isNull();
    }

    public void STATIONS_NOT_DELETED(final Station upStation, final Station downStation, final int expectedSize) {
        List<Station> stationIds = stationRepository.findAllById(Arrays.asList(upStation.getId(), downStation.getId()));
        assertThat(stationIds).hasSize(expectedSize);
    }

    public void DISTANCE_DELETED(final Long distanceId) {
        Distance foundDistance = distanceRepository.findById(distanceId).orElse(null);
        assertThat(foundDistance).isNull();
    }
}
