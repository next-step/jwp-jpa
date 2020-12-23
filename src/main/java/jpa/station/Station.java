package jpa.station;

import jpa.core.BaseEntity;
import jpa.line.Line;
import jpa.section.Section;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table
public class Station extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "start")
    private List<Section> startSections;

    @OneToMany
    @JoinColumn(name = "end")
    private List<Section> endSections;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public String getName() {
        return name;
    }

    public List<Line> getLines() {
        Set<Line> lines = new HashSet<Line>() {{
            if (startSections != null) {
                addAll(startSections.stream()
                        .map(Section::getLine)
                        .collect(Collectors.toList()));
            }
            if (endSections != null) {
                addAll(endSections.stream()
                        .map(Section::getLine)
                        .collect(Collectors.toList()));
            }
        }};
        return new ArrayList<>(lines);
    }

    public void addEndSection(Section section) {
        if(this.endSections == null){
            this.endSections = new ArrayList<>();
        }
        this.endSections.add(section);
    }

    public void addStartSection(Section section) {
        if(this.startSections == null){
            this.startSections = new ArrayList<>();
        }
        this.startSections.add(section);
    }

}
