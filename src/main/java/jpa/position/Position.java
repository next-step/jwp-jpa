package jpa.position;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import jpa.common.BaseTime;
import lombok.ToString;

@ToString
@Entity
public class Position extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int location;

	protected Position() {
	}

	public Position(int location) {
		this.location = location;
	}

}
