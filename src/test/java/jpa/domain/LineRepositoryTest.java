package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

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

    private Line getLineEntity() {
        return new Line("name", "yellow");
    }

    @Test
    @DisplayName("Line 이름으로 찾기 Test")
    void findByNameTest() {
        Line yellowLine = lineRepository.save(getLineEntity());

        Line savedLine = lineRepository.findByName(yellowLine.getName());

        assertThat(savedLine.getId()).isEqualTo(yellowLine.getId());
    }

    @Test
    @DisplayName("Line 색상으로 찾기 Test")
    void findByColorTest() {
        Line yellowLine = lineRepository.save(getLineEntity());

        Line savedLine = lineRepository.findByColor(yellowLine.getColor());

        assertThat(savedLine.getId()).isEqualTo(yellowLine.getId());
    }

}
