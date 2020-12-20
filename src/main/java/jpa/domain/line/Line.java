package jpa.domain.line;

import jpa.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    protected Line() {
    }

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
