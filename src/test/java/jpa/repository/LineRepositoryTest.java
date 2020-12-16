package jpa.repository;

import jpa.entity.Line;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @Test
    void save() {
        Line expected = new Line("군청색","1호선");
        Line actual = lineRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
        );
    }

    @Test
    void findByName() {
        String expected = "1호선";
        lineRepository.save(new Line("군청색", expected));
        String actual = lineRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }
}
