package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity {

	Favorite() {
	}
}
