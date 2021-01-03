package jpa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Section section;

    public Favorite() {}

    public Favorite(Station startStation, Station endStation) {
        //this.startStation = startStation;
        //this.endStation = endStation;
        this.section = new Section(startStation, endStation, 0);
    }

    public Long getId() {
        return this.id;
    }

    public Member getMember() {
        return member;
    }

    public Station getStartStation() {
        return section.getStartStation();
    }

    public Station getEndStation() {
        return section.getEndStation();
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
