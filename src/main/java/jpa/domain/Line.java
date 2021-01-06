package jpa.domain;

import javax.persistence.*;

@Entity
public class Line extends Common {
    private String name;

    private String color;

    @ManyToOne
    private Station station;

    public Line() {

    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void setStation(Station station) {
        this.station = station;
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
        return station;
    }
}
