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
        lineRepository.save(Line.of("몽촌토성역","분홍색"));
    }

    @Test
    @DisplayName("라인 추가")
    void save() {
        // given, when
        Line actual = lineRepository.save(Line.of("잠실역","초록색"));

        // then
        lineAssertAll(actual, "잠실역", "초록색");
    }

    @Test
    @DisplayName("라인 수정")
    void update() {
        // given
        String changeName = "의정부역";

        // when
        Line line = lineRepository.findByName("몽촌토성역");
        line.setName(changeName);
        Line actual = lineRepository.save(line);

        // then
        assertThat(actual.getName()).isEqualTo("의정부역");
    }

    @Test
    @DisplayName("name unique 속성 체크")
    void uniqueCheck() {
        // given when then
        assertThrows(DataIntegrityViolationException.class, () ->{
            lineRepository.save(Line.of("몽촌토성역","분홍색"));
        });
    }

    @Test
    @DisplayName("라인 삭제")
    void delete() {
        // given when
        lineRepository.delete(lineRepository.findByName("몽촌토성역"));

        // then
        Line actual = lineRepository.findByName("몽촌토성역");
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("라인 조회")
    void select() {
        // given when
        Line actual = lineRepository.findByName("몽촌토성역");

        // then
        lineAssertAll(actual, "몽촌토성역", "분홍색");
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