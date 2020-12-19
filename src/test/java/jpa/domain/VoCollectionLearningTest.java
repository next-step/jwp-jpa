package jpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VoCollectionLearningTest {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    void saveTest() {
        Station gangnam = new Station("gangnam");
        Station seocho = new Station("seocho");
        stationRepository.saveAll(Arrays.asList(gangnam, seocho));

        Section section = new Section(gangnam, seocho, 3L);

        Line line = new Line("red", "line2");
        line.addSection(section);

        lineRepository.save(line);

        Line foundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(foundLine).isNotNull();
        assertThat(foundLine).isEqualTo(line);
        assertThat(foundLine.getFirstDistance()).isEqualTo(section.getDistance());
    }
}
