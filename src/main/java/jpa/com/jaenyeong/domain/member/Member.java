package jpa.com.jaenyeong.domain.member;

import jpa.com.jaenyeong.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String email;
    private String password;

    public void changeMemberEmail(final String email) {
        this.email = email;
    }

    public void changeMemberAge(final int age) {
        this.age = age;
    }
}
