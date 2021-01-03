package jpa.domain.line;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    LineRepository lines;

    @Test
    void save() {
        final Line expected = new Line("2호선");
        final Line actual = lines.save(expected);
        assertThat(expected == actual).isTrue();
    }

    @Test
    void update() {
        // given
        Line line = lines.save(new Line("2호선"));

        // when
        String expectedName = "3호선";
        line.change(expectedName);
        lines.flush();

        assertThat(lines.findByName(expectedName)).isEqualTo(line);
    }

    @Test
    void delete() {
        // given
        Line line = lines.save(new Line("3호선", "red"));

        // when
        lines.delete(line);

        // then
        assertThat(lines.findByColor("red")).isNull();
    }
}
