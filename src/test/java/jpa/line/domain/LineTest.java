package jpa.line.domain;

import jpa.location.domain.Location;
import jpa.station.domain.Distance;
import jpa.station.domain.Station;
import jpa.station.domain.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineTest {
    @Autowired
    private LineRepository lines;
    @Autowired
    private StationRepository stations;
    private Line line;
    private Location location;

    @BeforeEach
    void beforeEach() {
        line = lines.save(new Line(LineType.SECOND_LINE));
        Station previousStation = stations.save(new Station("잠실새내"));
        Station station = stations.save(new Station("잠실"));
        Distance distance = new Distance(previousStation, 2000);
        location = new Location(line, station, distance);
    }

    @DisplayName("`Line` 객체 저장")
    @Test
    void save() {
        // Given
        Line line = new Line(LineType.FIRST_LINE);
        // When
        Line actual = lines.save(line);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, line)
        );
    }

    @DisplayName("이미 저장된 `Line`와 찾게된 `Line`의 동일성 여부 확인")
    @Test
    void findByName() {
        // When
        Line actual = lines.findByName(line.getName());
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, line)
        );
    }

    @DisplayName("`Line` 객체의 색상 변경")
    @Test
    void changeName() {
        // Given
        LineColor expected = LineColor.YELLOW;
        Line actual = lines.save(line);
        // When
        actual.changeColor(LineColor.YELLOW);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertThat(actual.getColor()).isEqualTo(expected)
        );
    }

    @DisplayName("트랜잭션 안에서 `Line` 객체 삭제")
    @Test
    void deleteInTransaction() {
        // When
        lines.delete(line);
        // Then
        assertNotNull(line);
    }

    @DisplayName("`Line`에서 하나의 `Station` 추가")
    @Test
    void addStation() {
        // Given
        Station seokChonStation = stations.save(new Station("석촌"));
        // When
        line.addStation(seokChonStation, location);
        // Then
        assertThat(line.getStations())
                .containsOnly(seokChonStation)
                .hasSize(1);
    }

    @DisplayName("`Line`에서 하나의 `Station` 삭제")
    @Test
    void removeStation() {
        // Given
        Station seokChonStation = stations.save(new Station("석촌"));
        line.addStation(seokChonStation, location);
        // When
        line.removeStation(seokChonStation);
        // Then
        assertThat(line.getStations()).isEmpty();
    }

    @DisplayName("`Line`에 속해있는 `Station` 확인")
    @Test
    void getStations() {
        // Given
        List<Station> expected = Arrays.asList(new Station("석촌"), new Station("강남"));
        expected.forEach(station -> line.addStation(stations.save(station), location));
        // When
        List<Station> actual = line.getStations();
        // Then
        assertThat(actual)
                .containsAll(expected)
                .hasSize(expected.size());
    }
}
