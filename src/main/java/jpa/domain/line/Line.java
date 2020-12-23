package jpa.domain.line;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.linestation.LineStation;

import javax.persistence.*;
import java.util.List;

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
}
