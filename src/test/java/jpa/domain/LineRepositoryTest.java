package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
@DataJpaTest
@DisplayName("Line Repository Test Class")
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;


    @Test
    @DisplayName("Line 이름으로 찾기 Test")
    void findByNameTest() {
        Line yellowLine = lineRepository.save(createLineEntity());

        Line savedLine = lineRepository.findByName(yellowLine.getName());

        assertThat(savedLine.getId()).isEqualTo(yellowLine.getId());
    }

    @Test
    @DisplayName("Line 색상으로 찾기 Test")
    void findByColorTest() {
        Line yellowLine = lineRepository.save(createLineEntity());

        Line savedLine = lineRepository.findByColor(yellowLine.getColor());

        assertThat(savedLine.getId()).isEqualTo(yellowLine.getId());
    }

    @Test
    @DisplayName("Line 생성시 에러 Test")
    void shouldBeExceptionCreateLineTest() {
        assertThatThrownBy(() -> new Line("name", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line의 name, color는 필수 값 입니다.");
    }

    private Line createLineEntity() {
        return new Line("name", "yellow");
    }

}
