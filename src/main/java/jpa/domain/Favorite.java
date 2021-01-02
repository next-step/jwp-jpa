package jpa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Favorite extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn
    private Station startStation;

    @OneToOne
    @JoinColumn
    private Station endStation;

    public Favorite() {}

    public Favorite(Station startStation, Station endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public Long getId() {
        return this.id;
    }

    public Member getMember() {
        return member;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }
        this.member = member;
        if (!member.getFavorites().contains(this)) {
            member.getFavorites().add(this);
        }
    }
}
