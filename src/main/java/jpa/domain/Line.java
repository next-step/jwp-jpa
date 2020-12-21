package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line extends BaseEntity{
    @Column(unique = true)
    private String name;
    private String color;

    protected Line() {}

    public Line(String name) {
        this.name = name;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
