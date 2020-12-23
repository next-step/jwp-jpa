package jpa.domain;

import javax.persistence.*;

@Entity
public class Line extends BaseEntity {

    @Column
    private String color;

    @Column(unique = true)
    private String name;

    protected Line() {
    }

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public void changeColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
