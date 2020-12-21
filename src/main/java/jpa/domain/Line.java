package jpa.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Entity
public class Line extends BaseEntity {

    private String color;

    @Column(unique = true)
    private String name;

    public Line() {
    }

    public Line(String name) {
        this.name = name;
    }
}
