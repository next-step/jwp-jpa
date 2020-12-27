package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    private LocalDateTime updateDate;
    @OneToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station startStation;
    @OneToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station endStations;

    public Favorite() {
    }

    public Favorite(Station startStation, Station endStation) {
        this.startStation = startStation;
        this.endStations = endStation;
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStations() {
        return endStations;
    }

    public void changeUpdateTime(LocalDateTime dateTime) {
        this.updateDate = dateTime;
    }
}
