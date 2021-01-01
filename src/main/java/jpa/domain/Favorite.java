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

    public Favorite() {}

    public Long getId() {
        return this.id;
    }

    public Member getMember() {
        return member;
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
