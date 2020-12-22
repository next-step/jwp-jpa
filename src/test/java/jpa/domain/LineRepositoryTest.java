package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    LineRepository lineRepository;

    @Test
    @DisplayName("line 저장 테스트")
    public void save() {
        // given
        Line line = new Line("4호선");

        // when
        Line saveLine = this.lineRepository.save(line);

        // then
        assertAll(
                () -> assertThat(saveLine.getId()).isNotNull(),
                () -> assertEquals(saveLine, line)
        );
    }

    @Test
    @DisplayName("이름으로 line 조회 테스트")
    public void findByName() {
        // given
        String name = "4호선";
        lineRepository.save(new Line(name));

        // when
        Line lineFindByName = this.lineRepository.findByName(name);

        // then
        assertThat(lineFindByName.getName()).isEqualTo(name);
    }
}
