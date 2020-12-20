package jpa.com.jaenyeong.domain.favorite;

import jpa.com.jaenyeong.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITE")
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
}
