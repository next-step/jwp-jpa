package jpa.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Section {

    @ManyToOne
    @JoinColumn
    private Station startStation;

    @ManyToOne
    @JoinColumn
    private Station endStation;

    @Column(nullable = true)
    private double distance;

    public Section() {
    }

    public Section(Station startStation, Station endStation, double distance) {
        if (startStation == null || endStation == null) {
            throw new IllegalArgumentException("구간 등록될 역이 존재하지 않습니다.");
        }
        this.startStation = startStation;
        this.endStation = endStation;
        this.distance = distance;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public double getDistance() {
        return distance;
    }

}
