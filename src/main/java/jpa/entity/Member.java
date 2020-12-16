package jpa.entity;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private Integer age;

    private String email;

    private String password;

    protected Member() {
    }

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
