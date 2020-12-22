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
public class Line extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String color;

  @OneToMany(mappedBy = "line", fetch = FetchType.LAZY)
  private List<LineStation> lineStations = new ArrayList<>();

  public Line(final String name, final String color) {
    this.name = name;
    this.color = color;
  }

  public void changeName(final String name) {
    this.name = name;
  }

  public void add(final LineStation lineStation) {
    this.lineStations.add(lineStation);
  }
}
