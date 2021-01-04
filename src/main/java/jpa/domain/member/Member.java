package jpa.domain.member;

import jpa.domain.base.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String email;
    private String password;

    public Member(String name) {
        validate(name);
        this.name = name;
    }

    protected Member() {
    }

    public Member(String name, String email, String password) {
        this(name);
        this.email = email;
        this.password = password;
    }

    private void validate(String name) {
        if (name == null) {
            throw new IllegalArgumentException("필수값 누락입니다.");
        }
    }

    public void change(String name) {
        validate(name);
        this.name = name;
    }

    public void change(int age) {
        validate(age);
        this.age = age;
    }

    private void validate(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("정확한 나이를 입력해주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
