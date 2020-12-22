package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Favorite extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_station_id")
    private Station fromStation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_station_id")
    private Station toStation;

    public Favorite(Member member) {
        this.member = member;
    }

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }
        this.member = member;
        member.getFavorites().add(this);
    }

    public void addStations(Station fromStation, Station toStation) {
        this.fromStation = fromStation;
        this.toStation = toStation;
    }
}
