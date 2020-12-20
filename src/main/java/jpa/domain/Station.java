package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Station extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "line_id")
  private Line line;

  public Station(final String name) {
    this.name = name;
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void setLine(final Line line) {
    this.line = line;
    line.getStations().add(this);
  }

}
