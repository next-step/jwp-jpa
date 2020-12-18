package jpa.dao;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;

@Entity
public class Line extends DefaultEntity {
    private String name;
    private String color;

    protected Line(){}

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Line{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
