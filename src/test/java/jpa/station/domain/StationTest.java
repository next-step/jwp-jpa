package jpa.station.domain;

import jpa.line.domain.Line;
import jpa.line.domain.LineColor;
import jpa.line.domain.LineRepository;
import jpa.line.domain.LineType;
import jpa.location.domain.Location;
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
class StationTest {
    @Autowired
    private StationRepository stations;
    @Autowired
    private LineRepository lines;
    private Station station;

    @BeforeEach
    void beforeEach() {
        station = new Station("잠실");
        stations.save(station);
    }

    @DisplayName("`Station` 객체 저장")
    @Test
    void save() {
        // Given
        Station station = new Station("강남");
        // When
        Station actual = stations.save(station);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, station)
        );
    }

    @DisplayName("이미 저장된 `Station`와 찾게된 `Station`의 동일성 여부 확인")
    @Test
    void findByName() {
        // When
        Station actual = stations.findByName("잠실");
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, station)
        );
    }

    @DisplayName("`Station` 객체의 이름 변경")
    @Test
    void changeName() {
        // Given
        String expected = "강남";
        // When
        station.changeName(expected);
        // Then
        assertAll(
                () -> assertNotNull(station),
                () -> assertThat(station.getName()).isEqualTo(expected)
        );
    }

    @DisplayName("트랜잭션 안에서 `Station` 객체 삭제")
    @Test
    void deleteInTransaction() {
        // When
        stations.delete(station);
        // Then
        assertNotNull(station);
    }

    @DisplayName("`Station`에 `Line` 추가")
    @Test
    void addLine() {
        // Given
        Line expected = new Line(LineType.SECOND_LINE);
        // When
        station.addLine(expected);
        // Then
        assertThat(station.getLines()).contains(expected);
    }

    @DisplayName("`Station`에서 하나의 `Line` 삭제")
    @Test
    void removeLine() {
        // Given
        Line line1 = lines.save(new Line(LineType.FIRST_LINE));
        station.addLine(line1);
        // When
        station.removeLine(line1);
        // Then
        assertThat(station.getLines()).isEmpty();
    }

    @DisplayName("`Station`이 어느 `Line`에 속해있는지 확인")
    @Test
    void getLines() {
        // Given
        List<Line> expected = Arrays.asList(new Line(LineType.FIRST_LINE), new Line(LineType.SECOND_LINE));
        expected.forEach(line -> station.addLine(lines.save(line)));
        // When
        List<Line> actual = station.getLines();
        // Then
        assertThat(actual)
                .containsAll(expected)
                .hasSize(expected.size());
    }

    @DisplayName("`Station`에 해당 노선에서 이전 역 정보 추가")
    @Test
    void getDistanceWithPreviousStation() {
        // Given
        Station previousStation = stations.save(new Station("잠실새내"));
        Line line = lines.save(new Line(LineType.SECOND_LINE));
        // When
        Distance distance = new Distance(previousStation, 2000L);
        Location expected = new Location(line, station, distance);
        station.addLocation(expected);
        // Then
        Location location = station.getLocations().stream().findAny().get();
        assertAll(
                () -> assertThat(station.getLocations()).hasSize(1),
                () -> assertSame(location, expected)
        );
    }
}
