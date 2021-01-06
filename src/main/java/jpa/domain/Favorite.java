package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Favorite extends Common{
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
        modified_date = LocalDateTime.now(zone);
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
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
