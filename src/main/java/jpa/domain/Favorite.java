package jpa.domain;

import javax.persistence.*;

@Entity
public class Favorite extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPART_STATION_ID")
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARRIVE_STATION_ID")
    private Station arrivalStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    protected Favorite() {
    }

    public Favorite(Station departureStation, Station arrivalStation, Member member) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.member = member;
        member.addFavorite(this);
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public Member getMember() {
        return member;
    }
}
