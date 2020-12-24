package jpa.repositories;

import jpa.domain.Distance;
import jpa.domain.Line;
import jpa.domain.LineStation;
import jpa.domain.Station;
import jpa.domain.repositories.LineRepository;
import jpa.domain.repositories.LineStationRepository;
import jpa.domain.repositories.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class LineStationRepositoryTest {

  @Autowired
  private LineStationRepository lineStations;

  @Autowired
  private LineRepository lines;

  @Autowired
  private StationRepository stations;

  @BeforeEach
  void before() {
    final Line lineFive = lines.save(new Line("5호선", "PURPLE"));
    final Line lineSeven = lines.save(new Line("7호선", "BROWN"));
    final Station 군자역 = stations.save(new Station("군자역"));
    final Station 건대역 = stations.save(new Station("건대역"));
    final Station 강남구청역 = stations.save(new Station("강남구청역"));

    final LineStation lineFive_군자역 = lineStations.save(new LineStation(lineFive, 건대역, 군자역, new Distance(100L)));
    final LineStation lineSeven_군자역 = lineStations.save(new LineStation(lineSeven, 건대역, 군자역, new Distance(100L)));
    final LineStation lineSeven_건대역 = lineStations.save(new LineStation(lineSeven, 강남구청역, 건대역, new Distance(200L)));
  }

  @DisplayName("노선 조회 시 여러 지하철역을 조회")
  @Test
  void station_list_by_line() {
    final Line lineFive = lines.findByName("5호선");
    final Line lineSeven = lines.findByName("7호선");

    assertThat(lineFive.getLineStations().size()).isEqualTo(1);
    assertThat(lineSeven.getLineStations().size()).isEqualTo(2);
  }

  @DisplayName("지하철 역 조회 시 여러 속한 라인(환승역)을 조회")
  @Test
  void line_list_by_station() {
    final Station 군자역 = stations.findByName("군자역");
    final Station 건대역 = stations.findByName("건대역");

    assertThat(군자역.getLineStations().size()).isEqualTo(2);
    assertThat(건대역.getLineStations().size()).isEqualTo(1);
  }

  @DisplayName("생성시 null check 예외")
  @Test
  void constructor_exception() {
    assertThatThrownBy(() -> {
      new LineStation(null, null, null, null);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void get_distance_between_station() {
    final LineStation lineFive_군자역 = lineStations.findByLineNameAndStationName("5호선", "군자역");
    final LineStation lineSeven_건대역 = lineStations.findByLineNameAndStationName("7호선", "건대역");

    assertThat(lineFive_군자역.getDistance().getDistance()).isEqualTo(100L);
    assertThat(lineSeven_건대역.getDistance().getDistance()).isEqualTo(200L);
  }
}