package jpa.member;

import com.sun.xml.bind.v2.model.core.ID;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * create table member (
 * id bigint not null auto_increment,
 * created_date datetime(6),
 * modified_date datetime(6),
 * age integer,
 * email varchar(255),
 * password varchar(255),
 * primary key (id)
 * ) engine=InnoDB
 */
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Member(String email, String password, int age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
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
