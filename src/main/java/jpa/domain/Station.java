package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Station extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
  private List<LineStation> lineStations = new ArrayList<>();

  public Station(final String name) {
    this.name = name;
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void add(final LineStation lineStation) {
    this.lineStations.add(lineStation);
  }
}
