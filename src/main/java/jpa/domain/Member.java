package jpa.domain;

import jpa.utils.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    private String email;

    private String password;

    protected Member() {
    }

    Member(final Long id, final Integer age, final String email, final String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Member(final Integer age, final String email, final String password) {
        this(null, age, email, password);
    }

    public void updateMember(final Member member) {
        this.age = member.age;
        this.email = member.email;
        this.password = member.password;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
