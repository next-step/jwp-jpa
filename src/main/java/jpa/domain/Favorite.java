package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Favorite extends Base {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<MemberFavorite> memberFavorites = new ArrayList<>();

    public Favorite(Station departureStation, Station arrivalStation) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }

    public static Favorite of(Station departureStation, Station arrivalStation) {
        return new Favorite(departureStation, arrivalStation);
    }
}
