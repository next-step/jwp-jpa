package jpa.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jpa.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "age")
	private int age;

	@Column(name = "email", length = 255)
	private String email;

	@Column(name = "password", length = 255)
	private String password;

	public Member(int age, String email, String password) {
		this.age = age;
		this.email = email;
		this.password = password;
	}

	public Member updateEmail(String email) {
		this.email = email;
		return this;
	}
}