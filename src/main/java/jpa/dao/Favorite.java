package jpa.dao;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Favorite extends DefaultEntity {
    @ManyToOne
    private Member member;
    @ManyToOne
    private Station departure;
    @ManyToOne
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
