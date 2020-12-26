package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void setUp() {
        lineRepository.save(new Line("1호선"));
        lineRepository.save(new Line("2호선", LineColor.GREEN));
    }

    @Test
    void save() {
        Line line = new Line("3호선", LineColor.ORANGE);
        Line actual = lineRepository.save(line);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getColor()).isEqualTo(line.getColor()),
                () -> assertThat(actual == line).isTrue()
        );
    }

    @Test
    void findByName() {
        Line actual = lineRepository.findByName("2호선");
        assertThat(actual.getName()).isEqualTo("2호선");
    }

    @Test
    void update_color() {
        Line line = lineRepository.findByName("1호선");
        assertThat(line.getColor()).isNull();

        line.changeColor(LineColor.BLUE);
        Line actual = lineRepository.findByName("1호선");

        assertThat(actual.getColor()).isEqualTo(LineColor.BLUE);
    }

    @Test
    void delete() {
        Line line = lineRepository.findByName("1호선");
        assertThat(line).isNotNull();

        lineRepository.delete(line);

        Line actual = lineRepository.findByName("1호선");
        assertThat(actual).isNull();
    }
}
