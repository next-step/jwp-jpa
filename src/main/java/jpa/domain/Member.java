package jpa.domain;

import javax.persistence.Entity;

@Entity
public class Member extends BaseTimeEntity {
    private Integer age;
    private String email;
    private String password;

    protected Member() {
    }

    public Member(Integer age, String password) {
        this.age = age;
        this.password = password;
    }

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
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

    public void changePassword(String password) {
        this.password = password;
    }
}
