package jpa.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import jpa.common.BaseTime;

@Entity
public class Member extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Integer age;

	@Column
	private String email;

	@Column(nullable = false)
	private String password;
}
