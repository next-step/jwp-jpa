package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Favorite extends BaseEntity{
    @OneToOne
    private Station startingStation;
    @OneToOne
    private Station arrivalStation;

    @ManyToOne
    private Member member;

    public Favorite() {

    }

    public Long getId() {
        return id;
    }

    public void chageDate(ZoneId zone) {
        modifiedDate = LocalDateTime.now(zone);
    }

    public LocalDateTime getCreated_date() {
        return createdDate;
    }

    public LocalDateTime getModified_date() {
        return modifiedDate;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setStarting(Station startingStation) {
        this.startingStation = startingStation;
    }

    public void setArrival(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Member getMemeber() {
        return member;
    }
}
