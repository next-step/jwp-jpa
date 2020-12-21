package jpa.repository;

import jpa.domain.Line;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Test
    void save() {
        Line expected = new Line("4호선");
        Line actual = lineRepository.save(expected);
        assertAll(
                ()->assertNotNull(actual.getId()),
                ()->assertEquals(actual.getName(), expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "4호선";
        lineRepository.save(new Line(expected));
        String actual = lineRepository.findByName(expected).getName();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("동일성 보장 기능 테스트")
    void identity() {
        Line line1 = lineRepository.save(new Line("4호선"));
        Line line2 = lineRepository.findById(line1.getId()).get();
        assertTrue(line1 == line2);
    }


}