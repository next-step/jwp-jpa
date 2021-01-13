package jpa.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "station")
public class Station extends BaseEntity {

    @Column(unique = true)
    private String name;

    @ManyToMany
    private List<Line> lines = new ArrayList<>();

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void addLines(Line line) {
        this.lines.add(line);
        line.addStation(this);
    }

}
