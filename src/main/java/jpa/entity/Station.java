package jpa.entity;


import jpa.FromTo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "station", uniqueConstraints = {@UniqueConstraint(
        name = "UK_gnneuc0peq2qi08yftdjhy7ok", columnNames = {"name"}
)})
public class Station extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;

    public Station(String name) {
        this.name = name;
    }

    public void setFavorite(Favorite favorite, FromTo fromTo) {
        if(Objects.nonNull(this.favorite)
                && this.favorite.getFromToStations().containsKey(fromTo)) {
            this.favorite.getFromToStations().remove(fromTo);
        }
        this.favorite = favorite;
        favorite.getFromToStations().put(fromTo, this);
    }
}
