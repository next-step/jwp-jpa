package jpa.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String email;
    private String password;
    private Integer age;

    @OneToMany
    private List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(String email, String password, Integer age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
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

    public void changePassword(String password) {
        this.password = password;
    }

    public void addFavorite(Favorite favorite){
        this.favorites.add(favorite);
    }

    public Favorite getFirstFavorite() {
        return this.favorites.get(0);
    }
}
