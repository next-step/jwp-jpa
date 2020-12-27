package jpa.domain;

import jpa.common.JpaAuditingDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "line")
public class Line extends JpaAuditingDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "station_line"
                , joinColumns = @JoinColumn(name = "line_id")
                , inverseJoinColumns = @JoinColumn(name = "station_id"))
    private List<Station> stations = new ArrayList<>();

    @OneToMany(mappedBy = "line")
    private List<Section> sections = new ArrayList<>();

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        this.stations.add(station);
        station.addLine(this);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSections(Section section) {
        this.sections.add(section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(id, line.id) &&
                Objects.equals(color, line.color) &&
                Objects.equals(name, line.name) &&
                Objects.equals(stations, line.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, name, stations);
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
