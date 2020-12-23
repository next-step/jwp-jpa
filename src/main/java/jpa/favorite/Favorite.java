package jpa.favorite;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import jpa.common.BaseTime;

@Entity
public class Favorite extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
