package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Line extends BaseEntity{

	private String color;
	@Column(unique = true)
	private String name;

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}
}
