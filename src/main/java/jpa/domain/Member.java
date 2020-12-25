package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    private LocalDateTime updateDate;
    @Column(name = "age")
    private Integer age;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany
    private List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(Integer age, String email) {
        this.age = age;
        this.email = email;
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

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }
}
