package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @DisplayName("저장된 엔티티는 자동으로 id를 부여받는다.")
    @Test
    void getIdByAutoAfterSaving() {
        LocalDateTime today = LocalDateTime.now();
        String testColor = "red";
        String testName = "test";

        Line line = new Line(today, today, testColor, testName);
        lineRepository.save(line);

        assertThat(line.getId()).isNotNull();
    }
}