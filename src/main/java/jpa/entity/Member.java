package jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    private int age;
    private String email;
    private String password;
}
