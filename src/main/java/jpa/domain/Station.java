package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    private LocalDateTime updateDate;
    @Column(name = "name", unique = true)
    private String name;
    @ManyToMany(mappedBy = "stations")
    private final List<Line> lines = new ArrayList<>();
    @Embedded
    private Distance distance;

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public Station(String name, Distance distance) {
        this.name = name;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public List<Line> getLines() {
        return lines;
    }

    public Distance getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != null ? !id.equals(station.id) : station.id != null) return false;
        if (createDate != null ? !createDate.equals(station.createDate) : station.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(station.updateDate) : station.updateDate != null) return false;
        if (name != null ? !name.equals(station.name) : station.name != null) return false;
        return lines != null ? lines.equals(station.lines) : station.lines == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lines != null ? lines.hashCode() : 0);
        return result;
    }
}
