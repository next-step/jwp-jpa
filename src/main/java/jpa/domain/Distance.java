package jpa.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Distance {
    private Long preStationId;
    private Integer length;

    public Distance() {
    }

    public Distance(Long preStationId, Integer length) {
        this.preStationId = preStationId;
        this.length = length;
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Integer getLength() {
        return length;
    }
}
