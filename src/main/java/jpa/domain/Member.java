package jpa.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_date;

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

    public Date getCreate_date() {
        return create_date;
    }

    public Date getModified_date() {
        return modified_date;
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
