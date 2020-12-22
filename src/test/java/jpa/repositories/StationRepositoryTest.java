package jpa.repositories;

import jpa.domain.Line;
import jpa.domain.Station;
import jpa.domain.repositories.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class StationRepositoryTest {

  @Autowired
  private StationRepository stations;

  @Test
  void save() {
    final Station expected = getStationSampleData();
    assertThat(expected.getId()).isNull();

    final Station actual = stations.save(expected);
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo("잠실역"),
        () -> assertThat(actual.getCreatedDate()).isNotNull(),
        () -> assertThat(actual.getModifiedDate()).isNotNull()
    );
  }

  @Test
  void fineByName() {
    final Station expected = getStationSampleData();
    stations.save(expected);

    final Station actual = stations.findByName("잠실역");
    assertThat(actual.getName()).isEqualTo("잠실역");
  }

  @Test
  void identity() {
    // id를 기반으로 같은 entity에 대해 동일성을 보장해준다.
    final Station station1 = stations.save(getStationSampleData());
    final Station station2 = stations.findById(station1.getId()).get();

    assertThat(station1 == station2).isTrue();
  }

  @Test
  void update() {
    final Station station = stations.save(getStationSampleData());
    station.changeName("잠실나루역");
    stations.flush();

    assertThat(station.getName()).isEqualTo("잠실나루역");
  }

  @Test
  void delete() {
    final Station station = stations.save(getStationSampleData());
    stations.delete(station);

    assertThat(stations.findById(station.getId()).orElse(null)).isNull();
  }

  private Station getStationSampleData() {
    return new Station("잠실역");
  }

  private Line getLineSampleData() {
    return new Line("8호선", "PINK");
  }
}