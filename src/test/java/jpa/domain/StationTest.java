package jpa.domain;

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
        station = new Station("석촌");
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
        Station actual = stations.findByName("석촌");
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
        Line expected = new Line(LineColor.GREEN, "2호선");
        // When
        station.addLine(expected);
        // Then
        assertThat(station.getLines()).contains(expected);
    }

    @DisplayName("`Station`에서 하나의 `Line` 삭제")
    @Test
    void removeLine() {
        // Given
        Line line1 = lines.save(new Line(LineColor.BLUE, "1호선"));
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
        List<Line> expected = Arrays.asList(new Line(LineColor.BLUE, "1호선"), new Line(LineColor.GREEN, "2호선"));
        expected.forEach(line -> station.addLine(lines.save(line)));
        // When
        List<Line> actual = station.getLines();
        // Then
        assertThat(actual)
                .containsAll(expected)
                .hasSize(expected.size());
    }
}
