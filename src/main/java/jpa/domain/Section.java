package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table
public class Section extends IdentifiedValueObject {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private Long distance;

    protected Section() {
    }

    Section (final Long id, final Station upStation, final Station downStation, final Long distance) {
        super.setId(id);
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Section(final Station upStation, final Station downStation, final Long distance) {
        this(null, upStation, downStation, distance);
    }

    public Long getDistance() {
        return this.distance;
    }

    public List<Station> getStations() {
        return Arrays.asList(upStation, downStation);
    }
}
