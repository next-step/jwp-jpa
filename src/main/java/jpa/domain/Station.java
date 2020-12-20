package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Station extends BaseEntity{
	@Column(unique = true)
	private String name;

	public String getName() {
		return name;
	}
}
