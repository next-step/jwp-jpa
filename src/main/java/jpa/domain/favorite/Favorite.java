package jpa.domain.favorite;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import jpa.domain.BaseEntity;

@Entity
public class Favorite extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;

	protected Favorite() {
	}

	public Long getId() {
		return id;
	}
}
