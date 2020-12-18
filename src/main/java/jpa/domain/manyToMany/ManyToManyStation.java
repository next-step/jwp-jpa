package jpa.domain.manyToMany;

import jpa.domain.Line;
import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ManyToManyStation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "stations")
    private List<ManyToManyLine> lines = new ArrayList<>();

    protected ManyToManyStation() {
    }

    ManyToManyStation(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public ManyToManyStation(final String name) {
        this(null, name);
    }

    public void addLine(final ManyToManyLine line) {
        this.lines.add(line);
    }

    public void updateStation(final ManyToManyStation station) {
        this.name = station.name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ManyToManyLine> getLines() {
        return lines;
    }
}
