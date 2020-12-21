package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Favorite extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }
        this.member = member;
        member.getFavorites().add(this);
    }
}
