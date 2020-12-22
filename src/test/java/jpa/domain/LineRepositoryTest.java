package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    LineRepository lines;

    @Test
    @DisplayName("라인 저장 테스트")
    public void save() throws Exception {
        Line expected = new Line("3호선", "주황");
        Line actual = lines.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("라인 ID로 조회 테스트")
    public void findById() throws Exception {
        Line expected = new Line("3호선", "주황");
        Line actual = lines.findById(lines.save(expected).getId()).get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("라인 Name 으로 조회 테스트")
    public void findByName() throws Exception {
        Line expected = new Line("3호선", "주황");
        lines.save(expected);
        Line actual = lines.findByName("3호선");
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    @DisplayName("라인 Color 로 조회 테스트")
    public void setColor() throws Exception {
        lines.save(new Line("3호선", "주황"));
        lines.save(new Line("4호선", "주황"));
        List<Line> byColor = lines.findByColor("주황");
        assertAll(
                () -> assertThat(byColor).isNotNull(),
                () -> assertThat(byColor).hasSize(2),
                () -> assertThat(byColor.get(0).getColor()).isEqualTo("주황")
        );
    }

    @Test
    @DisplayName("노선 조회 시 속한 지하철역을 볼 수 있다.")
    public void findLineWithStation() throws Exception {
        Line savedLine = lines.save(new Line("3호선", "주황"));
        Station station = new Station("화정역");

        savedLine.addStation(station);
        Line expected = lines.findByName(savedLine.getName());

        assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(savedLine.getName()),
                () -> assertThat(expected.getColor()).isEqualTo(savedLine.getColor()),
                () -> assertThat(expected.getStations()).contains(station)
        );
    }
}
