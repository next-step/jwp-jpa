package jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class Favorite extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_station")
    private Station sourceStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station")
    private Station destinationStation;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favorites")
    private final List<Member> members = new ArrayList<>();

    @Builder
    public Favorite(Station sourceStation, Station destinationStation) {
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public void updateSourceStation(Station station) {
        this.sourceStation = station;
    }

    public void updateDestinationStation(Station station) {
        this.destinationStation = station;
    }
}
