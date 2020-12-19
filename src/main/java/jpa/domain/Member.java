package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    public Member(final Integer age, final String email, final String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Member changeEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("올바른 이메일이 아닙니다");
        }
        this.email = email;
        return this;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
        favorite.changeMember(this);
    }

    public Favorite getFavorite(final Long favoriteId) {
        return favorites.stream()
                .filter(favorite -> favorite.isId(favoriteId))
                .findFirst()
                .orElse(null);
    }
}
