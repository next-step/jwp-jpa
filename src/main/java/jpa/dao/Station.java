package jpa.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends DefaultEntity {
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "station")
    private List<Distance> distances = new ArrayList<>();

    protected Station() {}

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                '}';
    }
}
