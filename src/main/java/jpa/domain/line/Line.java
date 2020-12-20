package jpa.domain.line;

import jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Line extends BaseEntity {

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "color")
    private String color;

    private Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static Line of(String name, String color) {
        return new Line(name, color);
    }

    public Line updateName(String name) {
        this.name = name;
        return this;
    }
}
