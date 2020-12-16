package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineRepositoryTest {
    private static final LocalDateTime TODAY = LocalDateTime.now();

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

    @DisplayName("영속성 컨텍스트에서 관리되는 엔티티는 더티 체킹을 통해 변경 내용이 자동으로 업데이트 된다.")
    @Test
    void dirtyCheckingTest() {
        String testColor = "red";
        String testName = "test";
        String changeColor = "blue";

        Line line = new Line(TODAY, TODAY, testColor, testName);
        lineRepository.save(line);
        assertThat(line.getColor()).isEqualTo(testColor);

        line.changeColor(changeColor);
        Line foundLine = lineRepository.findById(line.getId()).orElse(null);
        assertThat(foundLine.getColor()).isEqualTo(changeColor);
    }

    @DisplayName("쿼리 메서드를 통해 내장된 쿼리를 사용할 수 있다.")
    @Test
    void queryMethodTest() {
        String testColor = "red";
        String testName = "test";
        Line line = new Line(TODAY, TODAY, testColor, testName);
        lineRepository.save(line);

        Optional<Line> foundLine = lineRepository.findByColor(testColor);

        if (foundLine.isPresent()) {
            assertThat(foundLine.get().getColor()).isEqualTo(testColor);
            assertThat(foundLine.get().getName()).isEqualTo(testName);
        }
    }
}
