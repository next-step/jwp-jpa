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

    public Member() {
    }

    public Member(String email) {
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 일대다 단방향 연관 관계
    // 일대다 단방향 매핑보다는 다대일 양방향 매핑을 권장한다.
    @OneToMany
    @JoinColumn(name = "member_id")
    private final List<Favorite> favorites = new ArrayList<>();

    public void addFavorite(Favorite save) {
        favorites.add(save);
    }

}
