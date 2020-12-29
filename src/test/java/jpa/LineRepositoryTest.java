package jpa;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.station.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Test
    void save() {
        Line expected = new Line("2호선");
        Line actual = lineRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void findByName() {
        String expected = "2호선";
        lineRepository.save(new Line(expected));
        String actual = lineRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

}
