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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end")
    private Station end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;




}
