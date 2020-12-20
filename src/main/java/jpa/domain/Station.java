package jpa.domain;

import javax.persistence.*;

@Entity
public class Station extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  protected Station() {

  }

  public Station(final String name) {
    this.name = name;
  }

  public void changeName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
