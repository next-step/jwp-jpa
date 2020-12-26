package jpa.favorite.domain;

import jpa.domain.BaseEntity;
import jpa.station.domain.Station;
import jpa.member.domain.Member;

import javax.persistence.*;

@Entity
public class Favorite extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id", nullable = false)
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private Station destinationStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected Favorite() {
    }

    public Favorite(Station departureStation, Station destinationStation, Member member) {
        validate(departureStation, destinationStation, member);
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.member = member;
    }

    private void validate(Station departureStation, Station destinationStation, Member member) {
        if (departureStation == null) {
            throw new IllegalArgumentException("출발역은 비어있을 수 없습니다.");
        }
        if (destinationStation == null) {
            throw new IllegalArgumentException("도착역은 비어있을 수 없습니다.");
        }
        if (member == null) {
            throw new IllegalArgumentException("사용자는 비어있을 수 없습니다.");
        }
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public Member getMember() {
        return member;
    }
}
