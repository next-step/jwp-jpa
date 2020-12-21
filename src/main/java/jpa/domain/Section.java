package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Section extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    protected Section() {
    }

    Section(final Long id, final Station upStation, final Station downStation) {
        this.id = id;
        this.upStation = upStation;
        this.downStation = downStation;
    }

    public Section(final Station upStation, final Station downStation) {
        this(null, upStation, downStation);
    }

    public Long getId() {
        return this.id;
    }

    List<Station> getStations() {
        return Arrays.asList(upStation, downStation);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Section that = (Section) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
