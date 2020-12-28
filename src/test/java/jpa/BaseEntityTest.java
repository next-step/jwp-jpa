package jpa;

import jpa.line.Line;
import jpa.line.LineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class BaseEntityTest {

    @Autowired
    private LineRepository lineRepository;

    @Test
    void Auditing_테스트() {
        LocalDateTime now = LocalDateTime.of(2020, 12, 28, 14, 48);
        Line expected = new Line("2호선");
        lineRepository.save(expected);
        Line actual = lineRepository.findAll().get(0);
        assertAll(
                () -> assertThat(actual.getCreatedDate()).isAfter(now),
                () -> assertThat(actual.getModifiedDate()).isAfter(now)
        );
    }
}
