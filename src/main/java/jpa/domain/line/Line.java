package jpa.domain.line;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.linestation.LineStation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Line extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;

    @OneToMany(mappedBy = "line")
    private List<LineStation> station;

    protected Line() {
    }

    public Line(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(id, line.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
