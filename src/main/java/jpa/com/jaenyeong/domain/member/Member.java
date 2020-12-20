package jpa.com.jaenyeong.domain.member;

import jpa.com.jaenyeong.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String email;
    private String password;

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public Member setId(final Long id) {
        this.id = id;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Member setAge(final Integer age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Member setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Member setPassword(final String password) {
        this.password = password;
        return this;
    }
}
