package jpa.repository;

import jpa.line.Line;
import jpa.line.LineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class LIneRepositoryTest {

    @Autowired
    private LineRepository lines;

    @Test
    void save() {
        Line expected = new Line("2호선");
        Line actual = lines.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "2호선";
        lines.save(new Line(expected));
        String actual = lines.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }
}
