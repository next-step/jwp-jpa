package jpa.domain.manyToMany;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ManyToManyStation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "stations")
    private List<ManyToManyLine> lines;

    protected ManyToManyStation() {
    }

    ManyToManyStation(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public ManyToManyStation(final String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
