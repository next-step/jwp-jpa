package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Member extends BaseEntity{

	private String email;
	private String password;
	private int age;

	public Member() {
	}

	public Member(String email, String password, int age) {
		this.email = email;
		this.password = password;
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getAge() {
		return age;
	}
}
