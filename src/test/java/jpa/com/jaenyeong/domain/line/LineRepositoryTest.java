package jpa.com.jaenyeong.domain.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;

@DataJpaTest
@DisplayName("Line Repository 테스트")
class LineRepositoryTest {
    @Autowired
    private LineRepository lines;

    private Line greenLine;
    private Line blueLine;
    private Line purpleLIne;

    @BeforeEach
    void setUp() {
        greenLine = new Line("2호선", "green");
        blueLine = new Line("1호선", "blue");
        purpleLIne = new Line("5호선", "purple");
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

        final Line foundLine = lines.findByName(greenLine.getName())
            .orElseThrow(RuntimeException::new);

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
        assertSame(savedGreenLine.getColor(), "green");
        assertSame(lines.findByColor(LineColor.getColor("green")).size(), 1);

        savedGreenLine.changeLineColor("skyBlue");
        assertSame(lines.findByColor(LineColor.getColor("skyBlue")).size(), 1);
        assertSame(lines.findByColor(LineColor.getColor("green")).size(), 0);
    }

    @Test
    @DisplayName("객체 삭제 테스트")
    void delete() {
        final Line savedGreenLine = lines.save(greenLine);
        assertThat(savedGreenLine.getId()).isNotNull();

        lines.delete(savedGreenLine);
        assertThat(savedGreenLine).isNotNull();

        final Line foundByName = lines.findByName(savedGreenLine.getName())
            .orElse(null);
        final Line foundById = lines.findById(savedGreenLine.getId())
            .orElse(null);

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
