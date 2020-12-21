package jpa.com.jaenyeong.domain.favorite;

import jpa.com.jaenyeong.domain.BaseEntity;
import jpa.com.jaenyeong.domain.station.Station;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "departure_id")
    @OneToOne
    private Station departure;

    @JoinColumn(name = "arrival_id")
    @OneToOne
    private Station arrival;

    public Favorite(final Station departure, final Station arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    public Long getId() {
        return id;
    }

    public String getDepartureStationName() {
        return departure.getName();
    }

    public String getArrivalStationName() {
        return arrival.getName();
    }
}


