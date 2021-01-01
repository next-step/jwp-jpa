package jpa.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Member extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int age;

    @Column
    @Email(message = "Email should be valid")
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "member")
    private final List<Favorite> favorites = new ArrayList<>();

    public Member() {
    }

    public Member(String email) {
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
        favorite.setMember(this);
    }

}
