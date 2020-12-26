package jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Entity
public class Favorite extends BaseEntity {

    @ManyToOne
    private Station startStation;

    @ManyToOne
    private Station endStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Favorite() {
    }

    public Favorite(Station startStation, Station endStation, Member member) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.member = member;
        member.addFavorites(this);
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    @Override
    public String toString() {
        return "startStation=" + startStation +
                ", endStation=" + endStation;
    }
}
