package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "member")
class Member extends BaseEntity {

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
