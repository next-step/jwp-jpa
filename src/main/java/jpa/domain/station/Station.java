package jpa.domain.station;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.linestation.LineStation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
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
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null) {
            throw new IllegalArgumentException("필수값 누락입니다.");
        }
    }

    public void change(String name) {
        validate(name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

