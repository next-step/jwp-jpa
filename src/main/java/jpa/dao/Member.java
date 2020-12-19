package jpa.dao;

import javax.persistence.Entity;

@Entity
public class Member extends DefaultEntity {
    private Integer age;
    private String email;
    private String password;

    protected Member(){}

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member{" +
                "age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
