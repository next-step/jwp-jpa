package jpa.domain.member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import jpa.domain.BaseEntity;

@Entity
public class Member extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;

	private int age;

	private String email;

	private String password;

	protected Member() {
	}

	public Member(int age, String email, String password) {
		this.age = age;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
