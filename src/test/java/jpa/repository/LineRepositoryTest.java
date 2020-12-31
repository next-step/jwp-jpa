package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Test
    void save() {
        // given
        Line expected = new Line("2호선");

        // when
        Line actual = lineRepository.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        // given
        String expected = "2호선";
        lineRepository.save(new Line(expected));

        // when
        String actual = lineRepository.findByName(expected).getName();

        // then
        assertThat(actual).isEqualTo(expected);

        // 중복된 이름 insert 시 exception 발생 테스트
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            lineRepository.save(new Line(expected));
        });
    }

    @Test
    void identity() {
        Line line1 = lineRepository.save(new Line("2호선"));
        Line line2 = lineRepository.findById(line1.getId()).orElse(null);
        assertThat(line1 == line2).isTrue();
    }

    @Test
    void identityByName() {
        String name = "2호선";
        Line line1 = lineRepository.save(new Line(name));
        Line line2 = lineRepository.findByName(name);
        assertThat(line1 == line2).isTrue();
    }

    @Test
    void update() {
        Line line1 = lineRepository.save(new Line("2호선"));
        line1.setName("3호선");
        Line line2 = lineRepository.findByName("3호선");
        assertThat(line1 == line2).isTrue();
        //Line line2 = lineRepository.findById(line1.getId()).orElse(null);
        //assertThat(line2.getName()).isEqualTo("3호선");
    }

    @Test
    void delete() {
        Line line1 = lineRepository.save(new Line("2호선"));
        lineRepository.delete(line1);
        Line line2 = lineRepository.findByName("2호선");
        assertThat(line2).isNull();
    }

}