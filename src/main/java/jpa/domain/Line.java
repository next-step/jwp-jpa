package jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Line {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String color;

    @Column(unique = true)
    private String name;

    protected Line() {
    }
}
