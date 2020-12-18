package jpa.com.jaenyeong.domain.line;

import jpa.com.jaenyeong.domain.line.Line;
import jpa.com.jaenyeong.domain.line.LineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;

@DataJpaTest
// 테스트 시 H2 DB를 사용하기 위하여 애노테이션 추가
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// CreateDate, LastModifiedDate 초기화를 위해 추가
@EnableJpaAuditing
@DisplayName("Line Repository 테스트")
class LineRepositoryTest {
    @Autowired
    private LineRepository lines;

    private Line greenLine;
    private Line blueLine;
    private Line purpleLIne;

    @BeforeEach
    void setUp() {
        greenLine = new Line("2호선").setColor("Green");
        blueLine = new Line("1호선").setColor("Blue");
        purpleLIne = new Line("5호선").setColor("Purple");
    }

    @Test
    @DisplayName("객체 생성 테스트")
    void create() {
        assertThat(greenLine.getId()).isNull();
    }

    @Test
    @DisplayName("객체 저장 테스트")
    void save() {
        final Line savedGreenLine = lines.save(greenLine);

        assertAll(() -> assertThat(savedGreenLine.getId()).isNotNull(),
            () -> assertThat(savedGreenLine.getCreatedDate()).isNotNull(),
            () -> assertThat(savedGreenLine.getModifiedDate()).isNotNull());

        assertSame(greenLine, savedGreenLine);
        assertSame(greenLine.getId(), savedGreenLine.getId());
    }

    @Test
    @DisplayName("객체를 이름으로 찾는 테스트")
    void findByName() {
        final Line savedGreenLine = lines.save(greenLine);
        assertThat(savedGreenLine.getId()).isNotNull();

        final Line foundLine = lines.findByName(greenLine.getName());

        assertSame(savedGreenLine.getColor(), foundLine.getColor());
        assertSame(savedGreenLine, foundLine);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void findAll() {
        lines.save(greenLine);
        lines.save(blueLine);
        lines.save(purpleLIne);

        final List<Line> allLine = lines.findAll();
        assertSame(allLine.size(), 3);
    }

    @Test
    @DisplayName("객체 수정 테스트")
    void update() {
        final Line savedGreenLine = lines.save(greenLine);
        assertSame(savedGreenLine.getColor(), "Green");
        assertSame(lines.findByColor("Green").size(), 1);

        savedGreenLine.setColor("Red");
        assertSame(lines.findByColor("Red").size(), 1);
        assertSame(lines.findByColor("Green").size(), 0);
    }

    @Test
    @DisplayName("객체 삭제 테스트")
    void delete() {
        final Line savedGreenLine = lines.save(greenLine);
        assertThat(savedGreenLine.getId()).isNotNull();

        lines.delete(savedGreenLine);
        assertThat(savedGreenLine).isNotNull();

        final Line foundByName = lines.findByName(savedGreenLine.getName());
        final Line foundById = lines.findById(savedGreenLine.getId()).orElse(null);

        assertThat(foundByName).isNull();
        assertThat(foundById).isNull();
    }

    @Test
    @DisplayName("객체 전체 삭제 테스트")
    void deleteAll() {
        lines.save(greenLine);
        lines.save(blueLine);
        lines.save(purpleLIne);

        final List<Line> savedLines = lines.findAll();
        assertSame(savedLines.size(), 3);

        lines.deleteAll();
        final List<Line> expectedNothing = lines.findAll();

        assertSame(expectedNothing.size(), 0);
        assertSame(savedLines.size(), 3);
    }
}
