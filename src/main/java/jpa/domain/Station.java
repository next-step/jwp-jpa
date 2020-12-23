package jpa.domain;

import javax.persistence.*;

@Entity
public class Station extends BaseTimeEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINE_ID")
    private Line line;

    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeLine(final Line line) {
        this.line = line;
    }
}
