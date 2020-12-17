package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "favorite_station")
public class FavoriteStation extends BaseEntity {

    public enum Type {
        START, END
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_station_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public FavoriteStation(final Type type, final Favorite favorite, final Station station) {
        this.type = type;
        this.favorite = favorite;
        this.favorite.getFavoriteStations().add(this);
        this.station = station;
    }

    public boolean isStart() {
        return Type.START == this.type;
    }

    public boolean isEnd() {
        return Type.END == this.type;
    }
}
