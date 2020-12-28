package jpa.member;

import jpa.base.BaseEntity;
import jpa.favorite.Favorite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int age;

    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Favorite> favorites = new ArrayList<>();

    protected Member() {}

    public Member(int age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
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

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }
}
