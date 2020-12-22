package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity{
    @OneToOne
    private Station fromStation;
    @OneToOne
    private Station toStation;

    public static class Builder {
        private Station fromStation;
        private Station toStation;

        public Builder() {}

        public Builder fromStation(Station fromStation) {
            this.fromStation = fromStation;
            return this;
        }

        public Builder toStation(Station toStation) {
            this.toStation = toStation;
            return this;
        }

        public Favorite build() {
            return new Favorite(this);
        }
    }

    protected Favorite() {}

    private Favorite(Builder builder) {
        fromStation = builder.fromStation;
        toStation   = builder.toStation;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public Station getToStation() {
        return toStation;
    }
}
