package jpa.domain;

import jpa.common.JpaAuditingDate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Section extends JpaAuditingDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Line line;

    @ManyToOne
    private Station upwardStation;

    @ManyToOne
    private Station downStation;

    private int distance;

    protected Section() {
    }

    public Section(Line line, Station upwardStation, Station downStation, int distance) {
        this.setLine(line);
        this.setUpwardStation(upwardStation);
        this.setDownStation(downStation);
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Station getUpwardStation() {
        return upwardStation;
    }

    public void setUpwardStation(Station upwardStation) {
        this.upwardStation = upwardStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public void setDownStation(Station downStation) {
        this.downStation = downStation;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(id, section.id) &&
                Objects.equals(line, section.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, line);
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", line=" + line +
                '}';
    }
}
