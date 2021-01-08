package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {
    private int age;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    public Member() {

    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
