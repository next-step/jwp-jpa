package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private LineRepository lines;
    private Line line;

    @BeforeEach
    void beforeEach() {
        line = new Line("파란색", "1호선");
    }

    @DisplayName("`Line` 객체 저장")
    @Test
    void save() {
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
        // Given
        lines.save(line);
        // When
        Line actual = lines.findByName("1호선");
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
        String expected = "노란색";
        Line actual = lines.save(line);
        // When
        actual.changeColor("노란색");
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
}
