package jpa.entity;

import jpa.FromTo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Favorite extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "favorite")
    private Map<FromTo, Station> fromToStations = new HashMap<>();

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }
        this.member = member;
        member.getFavorites().add(this);
    }

    public void addFromToStations(Station station, FromTo fromTo) {
        fromToStations.put(fromTo, station);
        if(station.getFavorite() != this) {
            station.setFavorite(this, fromTo);
        }
    }
}
