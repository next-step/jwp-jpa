package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
public class Station extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private List<Line> lines = new ArrayList<>();

    protected Station() {}

    public Station(String name) {
        this.name = name;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public String getName() {
        return name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public Long getId() {
        return id;
    }
}
