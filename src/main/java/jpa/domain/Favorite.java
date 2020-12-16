package jpa.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jpa.infrastructure.jpa.BaseEntity;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */

@Entity
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_station_id")
    private Station sourceStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id")
    private Station destinyStation;

    protected Favorite() {
    }

    public Favorite(Station sourceStation, Station destinyStation) {
        this.sourceStation = sourceStation;
        this.destinyStation = destinyStation;
    }

    public Long getId() {
        return id;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public Station getDestinyStation() {
        return destinyStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
