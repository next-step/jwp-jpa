package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public Member(final Integer age, final String email) {
        this(age, email, "0000");
    }

    public Member(final Integer age, final String email, final String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Member changeEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("올바른 이메일이 아닙니다");
        }
        this.email = email;
        return this;
    }
}
