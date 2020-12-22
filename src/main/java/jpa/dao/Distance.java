package jpa.dao;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"line_id","station_id"})})
public class Distance extends DefaultEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station station;
    private Integer stationOrder;
    private Integer nextDistance;

    protected Distance(){}

    public Distance(Line line, Station station, Integer stationOrder, Integer nextDistance) {
        setLine(line);
        setStation(station);
        this.stationOrder = stationOrder;
        this.nextDistance = nextDistance;
    }

    public String getLineName() {
        return line.getName();
    }

    public String getStationName() {
        return station.getName();
    }

    public void setLine(Line line) {
        if ( this.line != null ) {
            this.line.getDistances().remove(this);
        }
        line.getDistances().add(this);
        this.line = line;
    }

    public void setStation(Station station) {
        if ( this.station != null ) {
            this.station.getDistances().remove(this);
        }
        station.getDistances().add(this);
        this.station = station;
    }

    public Integer getStationOrder() {
        return stationOrder;
    }

    public Integer getNextDistance() {
        return nextDistance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "line=" + line +
                ", station=" + station +
                ", stationOrder=" + stationOrder +
                ", nextDistance=" + nextDistance +
                '}';
    }
}
