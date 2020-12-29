package jpa.domain.member;

import jpa.domain.common.BaseTimeEntity;
import jpa.domain.favorite.Favorite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity {
    private int age;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private final List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(int age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
