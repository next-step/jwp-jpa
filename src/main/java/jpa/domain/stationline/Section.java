package jpa.domain.stationline;

import javax.persistence.Embeddable;

@Embeddable
public class Section {
    private String previousStationName;
    private int distance;

    protected Section() {
    }

    private Section(String previousStationName, int distance) {
        this.previousStationName = previousStationName;
        this.distance = distance;
    }

    public static Section of(String previousStationName, int distance) {
        return new Section(previousStationName, distance);
    }

    public String getPreviousStationName() {
        return previousStationName;
    }

    public int getDistance() {
        return distance;
    }

    public void setPreviousStationName(String previousStationName) {
        this.previousStationName = previousStationName;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
