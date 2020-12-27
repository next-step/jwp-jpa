package jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Setter
@Getter
@Embeddable
public class PreviousStation {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "previous_station_id")
    private Station previousStation;

    private Integer distance;

    public PreviousStation() {
    }

    public PreviousStation(Station previousStation, Integer distance) {
        this.previousStation = previousStation;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "PreviousStation{" +
                "previousStation=" + previousStation +
                ", distance=" + distance +
                '}';
    }
}
