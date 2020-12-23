package jpa.domain.station;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.favorite.Favorite;
import jpa.domain.line.Line;
import jpa.domain.linestation.LineStation;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "station")
public class Station extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "station")
    private List<LineStation> lineStations;

    protected Station() {
    }

    public Station(final String name) {
        this.name = name;
    }

    public Station(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

