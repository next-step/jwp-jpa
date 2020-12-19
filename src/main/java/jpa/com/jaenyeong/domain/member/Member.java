package jpa.com.jaenyeong.domain.member;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime modifiedDate;

    @Column
    Integer age;

    @Column
    String email;

    @Column
    String password;

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
