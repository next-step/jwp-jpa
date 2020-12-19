package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    private Station gangnam;
    private Station seocho;
    private Station samsung;

    @BeforeEach
    void setup() {
        gangnam = new Station("gangnam");
        seocho = new Station("seocho");
        samsung = new Station("samsung");

        stationRepository.saveAll(Arrays.asList(gangnam, seocho, samsung));
    }

    @DisplayName("일급 컬렉션 Sections를 이용해서 Section을 Line에 저장할 수 있다.")
    @Test
    void saveTest() {
        Section section = new Section(gangnam, seocho, 3L);

        Line line = new Line("red", "line2");
        line.addSection(section);

        lineRepository.save(line);

        Line foundLine = lineRepository.findByName(line.getName()).orElse(null);
        assertThat(foundLine).isNotNull();
        assertThat(foundLine).isEqualTo(line);
        assertThat(foundLine.getFirstDistance()).isEqualTo(section.getDistance());
    }

    @DisplayName("Line 조회 시 속한 역의 목록도 조회할 수 있다.")
    @Test
    void getStationsWhenGetLineTest() {
        int expectedStationsSize = 3;
        Section section1 = new Section(gangnam, seocho, 3L);
        Section section2 = new Section(seocho, samsung, 4L);

        Line lineNumberTwo = new Line("green", "line2");
        lineNumberTwo.addSection(section1);
        lineNumberTwo.addSection(section2);
        lineRepository.save(lineNumberTwo);

        Line foundLine = lineRepository.findByName(lineNumberTwo.getName()).orElse(null);
        assertThat(foundLine).isNotNull();

        assertThat(foundLine.getAllStations()).hasSize(expectedStationsSize);
    }
}
