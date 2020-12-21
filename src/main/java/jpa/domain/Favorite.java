package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity{

    protected Favorite() {}
}
