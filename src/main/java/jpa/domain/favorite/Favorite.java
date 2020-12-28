package jpa.domain;

import javax.persistence.*;

@Entity
public class Favorite extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depart_station_id")
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrive_station_id")
    private Station arrivalStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
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
}
