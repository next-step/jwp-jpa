package jpa;

import jpa.domain.Line;
import jpa.domain.LineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private LineRepository repository;

    @Test
    void save() {
        Line expected = new Line("green", "2호선");
        Line actual = repository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getColor()).isEqualTo(expected.getColor()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByColor() {
        String expected = "green";
        repository.save(new Line(expected, "2호선"));

        String actual = repository.findByColor(expected).getColor();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByName() {
        String expected = "2호선";
        repository.save(new Line("green", expected));
        String actual = repository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateColor() {
        Line expected = repository.save(new Line("green", "2호선"));

        expected.changeName("3호선");

        String actual = repository.findByName(expected.getName()).getName();
        assertThat(actual).isEqualTo(expected.getName());
    }

    @Test
    void deleteById() {
        Line expected = repository.save(new Line("green", "2호선"));

        repository.deleteById(expected.getId());
        repository.flush();

        Optional<Line> actual = repository.findById(expected.getId());
        assertThat(actual).isNotPresent();
    }
}