package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseEntity {
    private String name;

    private String color;

    @OneToMany(mappedBy = "line")
    private List<LineStation> lineStations = new ArrayList<>();

    @ManyToOne
    private LineStation lineStation;

    public Line() {

    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void setStation(Station station) {
        this.lineStation.setStation(station);
        //station.getLines().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Station getStation() {
        return lineStation.getStation();
    }
}
