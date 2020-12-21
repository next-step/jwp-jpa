package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
