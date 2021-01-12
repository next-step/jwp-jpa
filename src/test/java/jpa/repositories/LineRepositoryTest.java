package jpa.repositories;

import jpa.domain.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void setUp() {
        lineRepository.save(Line.of("8호선","분홍색"));
    }

    @Test
    @DisplayName("라인 추가")
    void save() {
        // given, when
        Line actual = lineRepository.save(Line.of("2호선","초록색"));

        // then
        lineAssertAll(actual, "2호선", "초록색");
    }

    @Test
    @DisplayName("라인 수정")
    void update() {
        // given
        String changeName = "1호선";

        // when
        Line line = lineRepository.findByName("8호선");
        line.setName(changeName);
        // 수정 확인에서는 사실상 불필요한 코드 테스트 목적 (해당 findByName 전에 flush 진행 후 조회)
        Line actual = lineRepository.findByName("1호선");

        // then
        assertThat(line == actual).isTrue();
        assertThat(actual.getName()).isEqualTo("1호선");
    }

    @Test
    @DisplayName("name unique 속성 체크")
    void uniqueCheck() {
        // given when then
        assertThrows(DataIntegrityViolationException.class, () ->{
            lineRepository.save(Line.of("8호선","분홍색"));
        });
    }

    @Test
    @DisplayName("라인 삭제")
    void delete() {
        // given when
        lineRepository.delete(lineRepository.findByName("8호선"));
        Line actual = lineRepository.findByName("8호선");

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("라인 조회")
    void select() {
        // given when
        Line actual = lineRepository.findByName("8호선");

        // then
        lineAssertAll(actual, "8호선", "분홍색");
    }

    private void lineAssertAll(Line actual,String expectedName, String expectedColor) {
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expectedName),
                () -> assertThat(actual.getColor()).isEqualTo(expectedColor),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull()
        );
    }
}