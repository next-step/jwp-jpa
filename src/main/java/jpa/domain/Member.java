package jpa.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity{
	@Column
	private String name;

	@Column(nullable = true)
	private int age;

	@Column(nullable = true)
	private String email;

	@Column(nullable = true)
	private String password;

	@OneToMany
	private List<Favorite> favoriteList = new ArrayList<>();

	public Member() {
	}

	public Member(String name, int age, String email, String password, List<Favorite> favoriteList) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.password = password;
		this.favoriteList = favoriteList;
	}

	public List<Favorite> getFavoriteList() {
		return favoriteList;
	}
}
