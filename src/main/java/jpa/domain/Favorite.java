package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class Favorite extends Base {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @ManyToOne
    private Member member;

    public Favorite(Station departureStation, Station arrivalStation) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }

    public static Favorite of(Station departureStation, Station arrivalStation) {
        return new Favorite(departureStation, arrivalStation);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
