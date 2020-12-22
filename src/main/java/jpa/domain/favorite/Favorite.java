package jpa.domain.favorite;

import jpa.domain.BaseEntity;
import jpa.domain.member.Member;
import jpa.domain.station.Station;

import javax.persistence.*;

@Entity
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    protected Favorite() {
    }

    public Favorite(Station departureStation, Station arrivalStation) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }
}
