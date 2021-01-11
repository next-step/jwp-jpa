package jpa.entity;

import lombok.*;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "member_favorite",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_id")
    )
    private List<Favorite> favorites = new ArrayList<>();

    @Builder
    public Member(long age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void addFavorite(Station sourceStation, Station destinationStation) {
        Favorite favorite = Favorite.builder().sourceStation(sourceStation).destinationStation(destinationStation).build();
        favorite.getMembers().add(this);
        this.favorites.add(favorite);
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void deleteFavorite(Favorite favorite) {
        favorite.getMembers().remove(this);
        this.favorites.remove(favorite);
    }
}
