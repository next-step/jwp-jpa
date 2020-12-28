package jpa.line;

import jpa.base.BaseEntity;
import jpa.route.Route;
import jpa.station.Station;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "route", joinColumns = @JoinColumn(name = "stationId"))
    private List<Route> routes = new ArrayList<>();

    protected Line() {}

    public Line(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void addRoute(Station fromStation, Station toStation, int distance) {
        routes.add(new Route(this, fromStation, toStation, distance, true));
        routes.add(new Route(this, fromStation, toStation, distance, false));
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
