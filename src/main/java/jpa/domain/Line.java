package jpa.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_date;

    private String color;

    private String name;

    public Line() {

    }
}
