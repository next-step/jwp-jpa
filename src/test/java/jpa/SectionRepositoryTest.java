package jpa;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.section.Section;
import jpa.section.SectionRepository;
import jpa.station.Station;
import jpa.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SectionRepositoryTest {

    private static final Long DISTANCE = 10L;

    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @BeforeEach
    public void setup() {
        Line line2 = new Line("2호선");
        Line line3 = new Line("3호선");
        Station gyodae = new Station("교대");
        Station seocho = new Station("서초");
        Station expressBusTerminal = new Station("고속터미널");

        lineRepository.save(line2);
        lineRepository.save(line3);
        stationRepository.save(gyodae);
        stationRepository.save(seocho);
        stationRepository.save(expressBusTerminal);
    }

    @Test
    void 저장() {
        Line line2 = lineRepository.findByName("2호선");
        Station gyodae = stationRepository.findByName("교대");
        Station seocho = stationRepository.findByName("서초");

        Section section1 = new Section(line2, gyodae, seocho, DISTANCE);
        sectionRepository.save(section1);

        assertAll(
                () -> assertThat(section1.getId()).isNotNull(),
                () -> assertThat(section1.getLine()).isEqualTo(line2),
                () -> assertThat(section1.getStart()).isEqualTo(gyodae),
                () -> assertThat(section1.getEnd()).isEqualTo(seocho),
                () -> assertThat(section1.getDistance()).isEqualTo(DISTANCE)
        );
    }

    @Test
    void 노선에서_역_조회() {
        Line line2 = lineRepository.findByName("2호선");
        Station gyodae = stationRepository.findByName("교대");
        Station seocho = stationRepository.findByName("서초");

        Section section1 = new Section(line2, gyodae, seocho, DISTANCE);
        line2.addSection(section1);

        assertAll(
                () -> assertThat(line2.getStations().size()).isEqualTo(2),
                () -> assertThat(line2.getStations().contains(gyodae)).isTrue(),
                () -> assertThat(line2.getStations().contains(seocho)).isTrue()
        );
    }

    @Test
    void 역에서_노선_조회() {
        Line line2 = lineRepository.findByName("2호선");
        Line line3 = lineRepository.findByName("3호선");
        Station gyodae = stationRepository.findByName("교대");
        Station seocho = stationRepository.findByName("서초");
        Station expressBusTerminal = stationRepository.findByName("고속터미널");
        Section section1 = new Section(line2, gyodae, seocho, DISTANCE);
        Section section2 = new Section(line3, gyodae, expressBusTerminal, DISTANCE);

        line2.addSection(section1);
        line3.addSection(section2);

        assertAll(
                () -> assertThat(gyodae.getLines().size()).isEqualTo(2),
                () -> assertThat(gyodae.getLines().contains(line2)).isTrue(),
                () -> assertThat(gyodae.getLines().contains(line3)).isTrue()
        );
    }

}
