package jpa.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Line extends BaseEntity {

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line")
    private List<StationLine> stationLines = new ArrayList<>();

    public Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public void addStationLines(StationLine stationLine) {
        stationLines.add(stationLine);
        stationLine.setLine(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(color, line.color) &&
                Objects.equals(name, line.name) &&
                Objects.equals(stationLines, line.stationLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, name, stationLines);
    }

    @Override
    public String toString() {
        return name;
    }
}
