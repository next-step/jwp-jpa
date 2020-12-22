package jpa.dao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Favorite extends DefaultEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station departure;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station arrival;

    protected Favorite(){}

    public Favorite(Member member, Station departure, Station arrival) {
        this.departure = departure;
        this.arrival = arrival;
        setMember(member);
    }

    public void setMember(Member member) {
        if( this.member != null ) {
            this.member.getFavorites().remove(this);
        }
        member.getFavorites().add(this);
        this.member = member;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "member=" + member +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }
}
