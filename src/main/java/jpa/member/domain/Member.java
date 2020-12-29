package jpa.member.domain;

import jpa.common.domain.BaseEntity;
import jpa.favorite.domain.Favorite;
import jpa.station.domain.Station;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "member_favorite",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_id")
    )
    private final List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void addFavorite(Station departure, Station destination) {
        Favorite favorite = new Favorite(departure, destination);
        favorite.getMembers().add(this);
        this.favorites.add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favorite.getMembers().remove(this);
        this.favorites.remove(favorite);
    }

    public List<Favorite> getFavorites() {
        return favorites;
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

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
