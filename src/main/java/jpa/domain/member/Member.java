package jpa.domain.member;

import jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private Member(int age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public static Member of(int age, String email, String password) {
        return new Member(age, email, password);
    }

    public Member updateEmail(String email) {
        this.email = email;
        return this;
    }
}
