package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "start_station_id")
    private Station startStation;

    @OneToOne
    @JoinColumn(name = "end_station_id")
    private Station endStation;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Favorite(final String name) {
        this.name = name;
    }

    public void setMember(final Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }
        this.member = member;
        this.member.getFavorites().add(this);
    }

    public void addStartStation(final Station station) {
        this.startStation = station;
    }

    public void addEndStation(final Station station) {
        this.endStation = station;
    }
}
