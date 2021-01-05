package jpa.domain;


import javax.persistence.*;

@Embeddable
public class Section {

    @ManyToOne
    @JoinColumn
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
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

    public Station getEndStation() {
        return endStation;
    }

    public double getDistance() {
        return distance;
    }

}
