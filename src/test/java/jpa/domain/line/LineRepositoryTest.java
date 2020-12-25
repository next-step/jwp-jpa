package jpa.domain.line;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {
    @Autowired
    LineRepository lines;

    @Test
    void save() {
        final Line expected = new Line("2호선");
        final Line actual = lines.save(expected);
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }
}
