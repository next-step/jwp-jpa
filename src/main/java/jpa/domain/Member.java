package jpa.domain;

import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import jpa.infrastructure.jpa.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Positive
    @Column(nullable = false)
    private int age;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(String email, String password, int age) {
        validate(email, password, age);
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public void add(Favorite favorite) {
        this.favorites.add(favorite);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    private void validate(String email, String password, int age) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Member name, password는 필수 값 입니다.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Member age는 0보다 커야합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
