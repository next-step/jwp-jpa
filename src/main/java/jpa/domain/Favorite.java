package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity{
    @OneToOne
    private Station fromStation;
    @OneToOne
    private Station toStation;

    protected Favorite() {}

    public Favorite(Station from, Station to) {
        this.fromStation = from;
        this.toStation = to;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public Station getToStation() {
        return toStation;
    }
}
