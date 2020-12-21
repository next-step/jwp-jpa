package jpa.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line extends DefaultEntity {
    @Column(unique = true)
    private String name;
    private String color;
    @OneToMany(mappedBy = "line")
    private List<Distance> distances = new ArrayList<>();

    protected Line(){}

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void updateName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Line{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
