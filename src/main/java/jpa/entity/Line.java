package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "UK_9ney9davbulf79nmn9vg6k7tn", columnNames = {"name"}
)})
public class Line extends BaseEntity {
    private String color;
    private String name;

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY)
    private List<StationLine> stationLines = new ArrayList<>();

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public void changeColor(String color) {
        this.color = color;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
