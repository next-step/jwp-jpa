package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "UK_9ney9davbulf79nmn9vg6k7tn", columnNames = {"name"}
)})
public class Line extends BaseEntity {
    private String color;
    private String name;

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }
}
