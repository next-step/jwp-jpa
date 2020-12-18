package jpa.domain.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LineStationId implements Serializable {

	private Long line;

	private Long station;
}
