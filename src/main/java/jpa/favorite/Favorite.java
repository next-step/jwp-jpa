package jpa.favorite;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * create table favorite (
 *     id bigint not null auto_increment,
 *     created_date datetime(6),
 *     modified_date datetime(6),
 *     primary key (id)
 * ) engine=InnoDB
 */
@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

}
