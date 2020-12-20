package jpa.domain;

import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "line")
public class Line {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date created_date;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date modified_date;

	@Column
	private String color;

	@Column(unique = true)
	private String name;


}
