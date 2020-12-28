package jpa.section;

import jpa.line.Line;
import jpa.station.Station;

import javax.persistence.*;

@Entity
@Table
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long distance;

    @ManyToOne
    @JoinColumn(name = "line")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start")
    private Station start;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end")
    private Station end;

    public Section() {
    }

    public Section(Line line, Station start, Station end, Long distance) {
        this.line = line;
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public Line getLine() {
        return line;
    }

    public Station getStart() {
        return start;
    }

    public Station getEnd() {
        return end;
    }

    public void setLine(Line line) {
        this.line = line;
        this.start.addStartSection(this);
        this.end.addEndSection(this);
    }

    public Long getId() {
        return this.id;
    }

    public Long getDistance() {
        return this.distance;
    }
}
