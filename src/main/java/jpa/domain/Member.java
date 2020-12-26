package jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
public class Member extends BaseEntity {

    private String name;
    private String age;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    public void addFavorites(Favorite favorite) {
        favorites.add(favorite);
        favorite.setMember(this);
    }

    public Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                '}';
    }
}
