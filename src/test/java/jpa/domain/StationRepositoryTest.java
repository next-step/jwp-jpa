package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stations;
    private Station station;

    @BeforeEach
    void beforeEach() {
        station = new Station("석촌");
    }

    @DisplayName("`Station` 객체 저장")
    @Test
    void save() {
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
        // Given
        stations.save(station);
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
        Station actual = stations.save(station);
        // When
        actual.changeName(expected);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertThat(actual.getName()).isEqualTo(expected)
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
}
