package jpa.com.jaenyeong.domain.member;

import jpa.com.jaenyeong.domain.BaseEntity;
import jpa.com.jaenyeong.domain.favorite.Favorite;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String email;
    private String password;

    @OneToMany
    private List<Favorite> favorites = new ArrayList<>();

    public Member(final int age, final String email, final String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void changeMemberEmail(final String email) {
        this.email = email;
    }

    public void changeMemberAge(final int age) {
        this.age = age;
    }

    public void addFavorite(final Favorite favorite) {
        favorites.add(favorite);
    }

    public void addFavorites(final List<Favorite> favorites) {
        this.favorites.addAll(favorites);
    }

    public int getFavoritesSize() {
        return favorites.size();
    }
}
