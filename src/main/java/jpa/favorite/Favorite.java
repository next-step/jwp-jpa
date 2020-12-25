package jpa.favorite;

import jpa.core.BaseEntity;
import jpa.member.Member;
import jpa.station.Station;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "start")
    private Station start;

    @ManyToOne
    @JoinColumn(name = "end")
    private Station end;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    public Station getStart() {
        return start;
    }

    public void setStart(Station start) {
        this.start = start;
    }

    public Station getEnd() {
        return end;
    }

    public void setEnd(Station end) {
        this.end = end;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
