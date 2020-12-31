package jpa.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Column(unique = true)
    private String name;

    public Station() {

    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }


    @OneToOne
    @JoinColumn(name = "line_station_id")
    private LineStation lineStation;


    public Station(String name, LineStation lineStation) {
        this.name = name;
        this.lineStation = lineStation;
    }

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    // 연관 관계 편의 메서드
    public void setLine(final Line line) {
        // 연관 관계 편의 메서드 작성 시 주의 사항
        if (Objects.nonNull(this.line)) {
            this.line.getStations().remove(this);
        }
        this.line = line;
        line.getStations().add(this);
    }

    public Line getLine() {
        return line;
    }

}
