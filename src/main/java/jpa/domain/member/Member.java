package jpa.domain.member;

import jpa.domain.base.BaseEntity;
import jpa.domain.favorite.Favorite;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorite = new ArrayList<>();

    @Builder
    private Member(int age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Member updateEmail(String email) {
        this.email = email;
        return this;
    }

    public void addFavorite(Favorite favorite) {
        this.favorite.add(favorite);
    }
}
