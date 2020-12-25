package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int age;

    private String email;

    private String password;
}
