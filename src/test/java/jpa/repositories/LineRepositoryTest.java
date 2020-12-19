package jpa.repositories;

import jpa.domain.Line;
import jpa.domain.repositories.LineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {
  @Autowired
  private LineRepository lines;

  @Test
  void save() {
    final Line expected = getLineSampleData();
    assertThat(expected.getId()).isNull();

    final Line actual = lines.save(expected);
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo("2호선"),
        () -> assertThat(actual.getColor()).isEqualTo("GREEN"),
        () -> assertThat(actual.getCreatedDate()).isNotNull(),
        () -> assertThat(actual.getModifiedDate()).isNotNull()
    );
  }

  @Test
  void fineByName() {
    final Line expected = getLineSampleData();
    lines.save(expected);

    final Line actual = lines.findByName("2호선");
    assertThat(actual.getName()).isEqualTo("2호선");
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

  private Line getLineSampleData() {
    return new Line("2호선", "GREEN");
  }
}