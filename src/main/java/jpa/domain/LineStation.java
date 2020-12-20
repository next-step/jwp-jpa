package jpa.domain;

import com.sun.istack.NotNull;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import jpa.infrastructure.jpa.BaseEntity;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-17
 */
@Entity
public class LineStation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    private Line line;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Embedded
    private Distance distance;

    protected LineStation() {
    }

    public LineStation(Line line, Station station, Distance distance) {
        validate(line, station);
        this.line = line;
        this.station = station;
        this.distance = distance;
        this.line.add(this);
        this.station.add(this);
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }

    public Distance getDistance() {
        return distance;
    }

    private void validate(Line line, Station station) {
        if (line == null || station == null) {
            throw new IllegalArgumentException("LineStation line, station는 필수 값 입니다.");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineStation that = (LineStation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
