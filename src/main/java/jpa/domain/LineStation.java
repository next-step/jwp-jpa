package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author : byungkyu
 * @date : 2020/12/21
 * @description :
 **/

@Entity
public class LineStation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;

	@OneToOne(mappedBy = "lineStation")
	private Station station;

	public LineStation() {
	}

	public LineStation(Line line, Station station) {
		this.line = line;
		this.station = station;
	}
}
