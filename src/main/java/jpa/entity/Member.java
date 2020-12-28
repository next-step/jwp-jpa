package jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "age")
    private long age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @Builder
    public Member(long age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }
}
