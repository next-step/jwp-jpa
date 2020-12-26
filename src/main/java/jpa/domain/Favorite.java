package jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Setter
@Getter
@Entity
public class Favorite extends BaseEntity {

    @OneToOne
    private Station startStation;

    @OneToOne
    private Station endStation;

    @ManyToOne
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

    @Override
    public String toString() {
        return "startStation=" + startStation +
                ", endStation=" + endStation;
    }
}
