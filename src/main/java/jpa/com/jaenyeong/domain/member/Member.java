package jpa.com.jaenyeong.domain.member;

import jpa.com.jaenyeong.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "MEMBER")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String email;
    private String password;

    protected Member() {
    }

    public void changeMemberEmail(final String email) {
        this.email = email;
    }

    public void changeMemberAge(final int age) {
        this.age = age;
    }
}
