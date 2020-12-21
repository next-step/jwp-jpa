package jpa.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-21
 */
@Embeddable
public class Distance {

    @PositiveOrZero
    private Integer distanceMeter;

    @OneToOne
    @JoinColumn(name = "distance_target_station_id")
    private Station distanceTargetStation;

    public Distance() {
    }

    public Distance(Integer distanceMeter, Station distanceTargetStation) {
        this.distanceMeter = distanceMeter;
        this.distanceTargetStation = distanceTargetStation;
    }

    public Integer getDistanceMeter() {
        return distanceMeter;
    }

    public Station getDistanceTargetStation() {
        return distanceTargetStation;
    }
}
