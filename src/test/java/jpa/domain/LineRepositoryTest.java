package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    LineRepository lineRepository;

    @Test
    @DisplayName("엔티티 저장 테스트")
    public void save() {
        // given
        Line line = new Line("4호선");

        // when
        Line saveLine = lineRepository.save(line);

        // then
        assertThat(saveLine).isEqualTo(line);
    }

    @Test
    @DisplayName("엔티티 조회 테스트")
    public void findByName() {
        // given
        String name = "4호선";
        lineRepository.save(new Line(name));

        // when
        Line lineFindByName = lineRepository.findByName(name);

        // then
        assertThat(lineFindByName.getName()).isEqualTo(name);
    }
}
