package jpa.repositories;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.domain.repositories.LineRepository;
import jpa.domain.repositories.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {
  @Autowired
  private LineRepository lines;
  @Autowired
  private StationRepository stations;

  @Test
  void save() {
    final Line expected = getLineSampleData();
    assertThat(expected.getId()).isNull();

    final Line actual = lines.save(expected);
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo("8호선"),
        () -> assertThat(actual.getColor()).isEqualTo("PINK"),
        () -> assertThat(actual.getCreatedDate()).isNotNull(),
        () -> assertThat(actual.getModifiedDate()).isNotNull()
    );
  }

  @Test
  void fineByName() {
    final Line expected = getLineSampleData();
    lines.save(expected);

    final Line actual = lines.findByName("8호선");
    assertThat(actual.getName()).isEqualTo("8호선");
  }

  @Test
  void identity() {
    final Line line1 = lines.save(getLineSampleData());
    final Line line2 = lines.findById(line1.getId()).get();

    assertThat(line1 == line2).isTrue();
  }

  @Test
  void update() {
    final Line line = lines.save(getLineSampleData());
    line.changeName("3호선");
    lines.flush();

    assertThat(line.getName()).isEqualTo("3호선");
  }

  @Test
  void delete() {
    final Line line = lines.save(getLineSampleData());
    lines.delete(line);

    assertThat(lines.findById(line.getId()).orElse(null)).isNull();
  }

  @Test
  void selectWithStations() {
    Line line = lines.findByName("1호선");

    assertAll(
        () -> assertThat(line.getStations().get(0).getName()).isEqualTo("시청역"),
        () -> assertThat(line.getStations().get(1).getName()).isEqualTo("종각역"),
        () -> assertThat(line.getStations().get(2).getName()).isEqualTo("종로3가역")
    );
  }

  @Test
  void saveWithStations() {
    Line expected = getLineSampleData();
    expected.addStation(stations.save(new Station("복정역")));
    lines.save(expected);
    lines.flush(); // transaction commit

    assertThat(expected.getStations().get(0).getName()).isEqualTo("복정역");
  }

  private Line getLineSampleData() {
    return new Line("8호선", "PINK");
  }
}