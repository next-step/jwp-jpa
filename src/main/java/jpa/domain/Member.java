package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date created_date;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date modified_date;

	private String name;

	@Column(nullable = true)
	private int age;

	@Column(nullable = true)
	private String email;

	@Column(nullable = true)
	private String password;

}
