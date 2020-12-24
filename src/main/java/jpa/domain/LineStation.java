package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LineStation extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "line_id", nullable = false)
  private Line line;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "previous_station_id", nullable = false)
  private Station previousStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "station_id", nullable = false)
  private Station station;

  @Embedded
  private Distance distance;

  public LineStation(final Line line, final Station previousStation, final Station station, final Distance distance) {
    if (line == null  || station == null) {
      throw new IllegalArgumentException("line, station 은 필수값입니다.");
    }
    this.line = line;
    this.previousStation = previousStation;
    this.station = station;
    this.distance = distance;
    line.add(this);
    station.add(this);
  }

}
