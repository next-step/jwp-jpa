package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    private int age;
    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Favorite> favorites = new ArrayList<>();


    public Member(int age) {
        this.age = age;
    }

    public Member(String email) {
        this.email = email;
    }

    public Member(int age, String email) {
        this.age = age;
        this.email = email;
    }

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
        if(favorite.getMember() != this) {
            favorite.setMember(this);
        }
    }
}
