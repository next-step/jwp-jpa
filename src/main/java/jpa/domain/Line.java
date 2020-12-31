package jpa.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Column
    private String color;

    //@Column(nullable = false)
    @Column(unique = true)
    private String name;

    public Line() {

    }

    public Line(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "line")
    private final List<Station> stations = new ArrayList<>();

    public List<Station> getStations() {
        return stations;
    }

    // 연관 관계 편의 메서드
    public void addStation(Station station) {
        stations.add(station);
        station.setLine(this);
    }

}
