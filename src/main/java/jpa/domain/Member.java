package jpa.domain;

import javax.persistence.*;

@Entity
public class Member extends Common {
    private int age;

    private String email;

    private String password;

    public Member() {

    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
