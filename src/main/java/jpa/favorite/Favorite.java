package jpa.favorite;

import jpa.core.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
