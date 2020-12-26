package jpa.com.jaenyeong.domain.distance;

import jpa.com.jaenyeong.domain.station.EmptyStation;

public class EmptyDistance extends Distance {
    public EmptyDistance() {
        super(new EmptyStation(), 0);
    }
}
