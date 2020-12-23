package jpa.domain.line;

import jpa.domain.BaseEntity;
import jpa.domain.linestation.LineStation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line")
    private final List<LineStation> lineStations = new ArrayList<>();

    protected Line() {
    }

    public Line(String color, String name) {
        this.color = color;
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

    public List<LineStation> getLineStations() {
        return Collections.unmodifiableList(lineStations);
    }

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

}
