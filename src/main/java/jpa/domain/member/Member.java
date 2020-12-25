package jpa.domain.member;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.favorite.Favorite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }

    protected Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void changeName(String name) {
        this.name =  name;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void addFavorites(Favorite favorite) {
        this.favorites.add(favorite);
        favorite.setMember(this);
    }
}
