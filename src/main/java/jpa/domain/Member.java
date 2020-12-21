package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "age", nullable = false)
	private int age;

	@Column(name = "email", nullable = false, length = 255)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	Member(int age, String email, String password) {
		this.age = age;
		this.email = email;
		this.password = password;
	}

	Long getId() {
		return id;
	}

	int getAge() {
		return age;
	}

	String getEmail() {
		return email;
	}

	String getPassword() {
		return password;
	}
}
