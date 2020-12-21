package jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    private int age;
    private String email;


    public Member(int age) {
        this.age = age;
    }

    public Member(String email) {
        this.email = email;
    }

    public Member(int age, String email) {
        this.age = age;
        this.email = email;
    }
}
