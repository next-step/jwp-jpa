package jpa.domain.member;

import jpa.domain.base.BaseTimeEntity;
import jpa.domain.favorite.Favorite;

import javax.persistence.*;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites;

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
}
