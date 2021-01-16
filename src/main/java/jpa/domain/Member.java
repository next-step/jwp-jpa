package jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member extends Base {

    @Min(value = 0, message = "나이는 0세 이상이여야 합니다.")
    @Max(value = 200, message = "나이는 200세 이하이여야 합니다.")
    private int age;

    @Email(message = "유효하지 않은 이메일입니다.")
    private String email;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Favorite> favorites = new ArrayList<>();

    public void setFavorite(Favorite favorite) {
        favorites.add(favorite);
        favorite.setMember(this);
    }

    public Member(int age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public static Member of(int age, String email, String password) {
        return new Member(age, email, password);
    }
}
