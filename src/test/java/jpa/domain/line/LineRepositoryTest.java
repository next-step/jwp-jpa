package jpa.domain.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void setUp() {
        lineRepository.save(Line.of("4호선", "파랑색"));
        lineRepository.save(Line.of("2호선", "초록색"));
    }

    @Test
    @DisplayName("Line 추가 테스트")
    void insert_line_test() {
        // given
        Line line = Line.of("8호선", "분홍색");

        // when
        Line persistLine = lineRepository.save(line);

        // then
        assertThat(persistLine.getId()).isNotNull();
        assertThat(persistLine.getCreatedDate()).isNotNull();
        assertThat(persistLine.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Line 조회 테스트")
    void select_line_test() {
        // given
        Line line = Line.of("8호선", "분홍색");

        // when
        Line persistLine = lineRepository.save(line);

        // then
        assertAll(
                () -> assertThat(persistLine.getId()).isNotNull(),
                () -> assertThat(persistLine.getName()).isEqualTo("8호선"),
                () -> assertThat(persistLine.getColor()).isEqualTo("분홍색")
        );
    }

    @Test
    @DisplayName("Line 전체 조회 테스트")
    void select_all_line_test() {
        // given
        Line line = Line.of("8호선", "분홍색");
        lineRepository.save(line);

        // when
        List<Line> lines = lineRepository.findAll();
        List<String> lineNames = lines.stream()
                .map(Line::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(lineNames.size()).isEqualTo(3),
                () -> assertThat(lineNames).contains("8호선", "4호선", "2호선")
        );
    }

    @Test
    @DisplayName("Line 수정 테스트")
    void update_line_test() {
        // given
        String changeLineName = "1호선";

        // when
        Line line = lineRepository.findByName("4호선");
        line.updateName(changeLineName);

        Line updatedLine = lineRepository.findByName(changeLineName);

        // then
        assertAll(
                () -> assertThat(updatedLine.getName()).isEqualTo("1호선"),
                () -> assertThat(updatedLine.getColor()).isEqualTo("파랑색")
        );
    }

    @Test
    @DisplayName("Line 삭제 테스트")
    void delete_line_test() {
        // given
        Line line = lineRepository.findByName("2호선");

        // when
        lineRepository.delete(line);

        // then
        Line deletedLine = lineRepository.findByName("2호선");
        assertThat(deletedLine).isNull();
    }

}
