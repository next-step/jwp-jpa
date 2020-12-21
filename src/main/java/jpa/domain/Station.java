package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Station extends BaseEntity {
	@Column(unique = true)
	private String name;


	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;


	public Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public Line getLine() {
		return line;
	}

	public void changeLine(Line line) {
		this.line = line;
	}
}
