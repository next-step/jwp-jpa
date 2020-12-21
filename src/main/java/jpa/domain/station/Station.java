package jpa.domain.station;

import jpa.domain.BaseEntity;
import jpa.domain.line.Line;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "stations")
    private final List<Line> lines = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Line> getLines() {
        return lines;
    }

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }
}
