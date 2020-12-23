package jpa.domain;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String password;

    protected Member() {
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

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
