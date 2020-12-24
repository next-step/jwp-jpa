package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    private LineRepository lines;

    @Test
    void save() {
        Line expected = new Line("3호선", "orange");
        Line actual = lines.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "3호선";
        lines.save(new Line(expected));
        String actual = lines.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }
}
