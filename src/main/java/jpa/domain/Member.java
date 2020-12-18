package jpa.domain;

import javax.persistence.Entity;

@Entity
public class Member extends BaseEntity{

	private int age;

	private String email;

	private String password;

	protected Member() {
	}

	public Member(int age, String email, String password) {
		this.email = email;
		this.password = password;
		this.age = age;
	}

	public Member(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void changeEmail(String email) {
		this.email = email;
	}
}
