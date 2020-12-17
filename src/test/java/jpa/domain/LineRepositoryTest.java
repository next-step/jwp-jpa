package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LineRepository lineRepository;

    @DisplayName("저장된 엔티티는 자동으로 id를 부여받는다.")
    @Test
    void getIdByAutoAfterSaving() {
        String testColor = "red";
        String testName = "test";

        Line line = new Line(testColor, testName);
        lineRepository.save(line);

        assertThat(line.getId()).isNotNull();
    }

    @DisplayName("영속성 컨텍스트에서 관리되는 엔티티는 더티 체킹을 통해 변경 내용이 자동으로 업데이트 된다.")
    @Test
    void dirtyCheckingTest() {
        String testColor = "red";
        String testName = "test";
        String changeColor = "blue";

        Line line = new Line(testColor, testName);
        lineRepository.save(line);
        assertThat(line.getColor()).isEqualTo(testColor);

        line.updateLine(new Line(changeColor, testName));
        Line foundLine = lineRepository.findById(line.getId()).orElse(null);
        assertThat(foundLine.getColor()).isEqualTo(changeColor);
    }

    @DisplayName("쿼리 메서드를 통해 내장된 쿼리를 사용할 수 있다.")
    @Test
    void queryMethodTest() {
        String testColor = "red";
        String testName = "test";
        Line line = new Line(testColor, testName);
        lineRepository.save(line);

        Line foundLine = lineRepository.findByColor(testColor).orElse(null);

        assertThat(foundLine.getColor()).isEqualTo(testColor);
        assertThat(foundLine.getName()).isEqualTo(testName);
    }

    @DisplayName("JPA 어노테이션을 통한 name 칼럼 unique 제약 조건이 정상 동작한다.")
    @Test
    void uniqueConstraintTest() {
        String testName = "sameName";
        Line line = new Line("red", testName);

        lineRepository.save(line);
        // entityManager.flush();      // 영속성 컨텍스트를 DB에 반영하여 DB에 걸린 unique 조건을 확인하기 위한 flush

        assertThatThrownBy(() -> {
            lineRepository.save(new Line("blue", line.getName()));
            // entityManager.flush();  // 영속성 컨텍스트를 DB에 반영하여 DB에 걸린 unique 조건을 확인하기 위한 flush
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
