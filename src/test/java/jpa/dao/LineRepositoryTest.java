package jpa.dao;

import jpa.domain.Line;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    private LineRepository lines;

    @Test
    void save() {
        Line expected = new Line("2호선", "green");
        Line actual = lines.save(expected);
        assertThat(actual == expected).isTrue();
    }

    @Test
    void findByName() {
        Line expected1 = new Line("9호선", "gold");
        lines.save(expected1);
        Line expected2 = new Line("2호선", "green");
        lines.save(expected2);
        Line actual1 = lines.findByName("9호선");
        assertThat(actual1.getColor()).isEqualTo("gold");
        Line actual2 = lines.findByName("2호선");
        assertThat(actual2.getColor()).isEqualTo("green");
    }

    @Test
    void remove() {
        Line expected = new Line("9호선", "gold");
        Line actual1 = lines.save(expected);
        assertThat(actual1).isNotNull();
        lines.deleteById(actual1.getId());
        List<Line> actual2 = lines.findAll();
        assertThat(actual2).isEmpty();
    }
}
