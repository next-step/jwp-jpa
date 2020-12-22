package jpa.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends DefaultEntity {
    private Integer age;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites = new ArrayList<>();

    protected Member(){}

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    @Override
    public String toString() {
        return "Member{" +
                "age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
