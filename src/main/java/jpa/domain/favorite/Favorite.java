package jpa.domain.favorite;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.member.Member;
import jpa.domain.station.Station;

import javax.persistence.*;


@Entity
public class Favorite extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id")
    private Station depatureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Favorite() {
    }

    public Favorite(Station depatureStation, Station arrivalStation) {
        this.depatureStation = depatureStation;
        this.arrivalStation = arrivalStation;
    }
}
