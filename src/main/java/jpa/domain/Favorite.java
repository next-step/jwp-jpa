package jpa.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Entity
public class Favorite extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station startStation;

    @OneToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station endStation;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Favorite() {
    }
}
