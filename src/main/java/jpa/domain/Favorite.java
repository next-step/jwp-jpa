package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date created_date;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date modified_date;

}
