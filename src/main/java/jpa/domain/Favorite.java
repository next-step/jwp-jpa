package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "start_station_id")
  private Station startStation;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "end_station_id")
  private Station endStation;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id")
  private Member member;

  public Favorite(final String name, final Station startStation, final Station endStation) {
    this.name = name;
    this.startStation = startStation;
    this.endStation = endStation;
  }

  public void setMember(final Member member) {
    if (Objects.nonNull(this.member)) {
      this.member.getFavorites().remove(this);
    }
    if (!member.getFavorites().contains(this)) {
      member.getFavorites().add(this);
    }
    this.member = member;
  }
}
