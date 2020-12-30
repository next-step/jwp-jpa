package jpa.line;

import jpa.core.BaseEntity;
import jpa.section.Section;
import jpa.station.Station;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;

    @OneToMany
    @JoinColumn(name = "line")
    private List<Section> sections;

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public List<Station> getStations() {
        Set<Station> stations = new HashSet<>();
        sections.forEach(item -> {
            stations.add(item.getStart());
            stations.add(item.getEnd());
        });
        return new ArrayList<>(stations);
    }

    public void addSection(Section section) {
        if (this.sections == null) {
            this.sections = new ArrayList<>();
        }
        this.sections.add(section);
        section.setLine(this);
    }

}
