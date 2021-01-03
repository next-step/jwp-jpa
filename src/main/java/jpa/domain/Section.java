package jpa.domain;


import javax.persistence.*;

@Embeddable
public class Section {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @ManyToOne
    @JoinColumn
    private Station startStation;

    @ManyToOne
    @JoinColumn
    private Station endStation;

    @Column(nullable = true)
    private double distance;

    public Section() {
    }

    public Section(Station startStation, Station endStation, double distance) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.distance = distance;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

}
