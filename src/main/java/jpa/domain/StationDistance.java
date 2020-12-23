package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StationDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "current_station_id")
    private Station current;

    @ManyToOne
    @JoinColumn(name = "from_station_id")
    private Station from;

    @Column
    private Integer distance;

    public StationDistance(Station current, Station from, int distance) {
        this.from = from;
        this.current = current;
        this.distance = distance;

        current.addByStationDistance(this);
    }
}
