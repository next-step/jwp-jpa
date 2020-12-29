package jpa.favorite;

import jpa.base.BaseEntity;
import jpa.member.Member;
import jpa.station.Station;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
public class Favorite extends BaseEntity {

    private static final String SAME_STATION_MESSAGE = "출발역과 도착역은 같을 수 없습니다.";

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "from_station_id", insertable = false, updatable = false)
    private Station fromStation;

    @ManyToOne
    @JoinColumn(name = "to_station_id", insertable = false, updatable = false)
    private Station toStation;

    protected Favorite() {}

    public Favorite(Member member, Station fromStation, Station toStation) {
        checkArgument(!fromStation.equals(toStation), SAME_STATION_MESSAGE);

        this.member = member;
        this.fromStation = fromStation;
        this.toStation = toStation;

        if (!member.getFavorites().contains(this)) {
            this.member.addFavorite(this);
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public Station getToStation() {
        return toStation;
    }
}
