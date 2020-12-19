package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    private LocalDateTime updateDate;
    private Integer age;
    private String email;
    private String password;

    public Member(Integer age, String email) {
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
