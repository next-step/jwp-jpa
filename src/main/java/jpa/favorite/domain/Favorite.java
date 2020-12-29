package jpa.favorite.domain;

import jpa.common.domain.BaseEntity;
import jpa.member.domain.Member;
import jpa.station.domain.Station;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Favorite extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id", nullable = false)
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private Station destinationStation;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favorites")
    private final List<Member> members = new ArrayList<>();

    protected Favorite() {
    }

    public Favorite(Station departureStation, Station destinationStation) {
        validate(departureStation, destinationStation);
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
    }

    private void validate(Station departureStation, Station destinationStation) {
        if (departureStation == null) {
            throw new IllegalArgumentException("출발역은 비어있을 수 없습니다.");
        }
        if (destinationStation == null) {
            throw new IllegalArgumentException("도착역은 비어있을 수 없습니다.");
        }
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public List<Member> getMembers() {
        return members;
    }
}
