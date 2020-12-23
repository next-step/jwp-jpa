package jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Member extends BaseEntity {

    private String age;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites = new ArrayList<>();
}
