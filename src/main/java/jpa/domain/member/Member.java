package jpa.domain.member;

import javax.persistence.Entity;

import jpa.domain.BaseEntity;

@Entity
public class Member extends BaseEntity {
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
