package jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class Member extends BaseEntity {

    private String age;
    private String email;
    private String password;
}
