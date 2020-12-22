package jpa.domain.member;

import jpa.domain.BaseEntity;
import jpa.domain.favorite.Favorite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private final List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Integer getAge() {
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
        favorite.changeMember(this);
    }
}
