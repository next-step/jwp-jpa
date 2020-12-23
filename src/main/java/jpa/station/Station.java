package jpa.station;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import jpa.common.BaseTime;
import jpa.line.Line;
import lombok.Getter;
import lombok.ToString;

@ToString
@Entity
@Table(indexes = @Index(name = "unique_station_name", columnList = "name", unique = true))
public class Station extends BaseTime {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Column(nullable = false)
	private String name;

	@Getter
	@ManyToMany
	@JoinColumn
	private List<Line> lines = new ArrayList<>();

	protected Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	/**
	 * 연관관계 편의 메서드
	 * @param line: 라인 추가
	 */
	public void addLine(final Line line) {
		line.getStations().add(this);
		this.lines.add(line);
	}

	/**
	 * 연관관계 편의 메서드
	 * @param line: 라인 제거
	 */
	public void clearLine(final Line line) {
		line.getStations().remove(this);
		this.lines.remove(line);
	}

}
