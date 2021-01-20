package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class MemberFavorite extends Base {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Favorite favorite;

    public MemberFavorite(Member member, Favorite favorite) {
        this.member = member;
        this.favorite = favorite;
    }

    public static MemberFavorite of(Member member, Favorite favorite) {
        return new MemberFavorite(member, favorite);
    }
}
