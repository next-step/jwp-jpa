package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
class Member extends BaseEntity {

	@Column(name = "age", nullable = false)
	private int age;

	@Column(name = "email", nullable = false, length = 255)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Favorite> favorites;

	protected Member() {
	}

	public Member(int age, String email, String password) {
		this.age = age;
		this.email = email;
		this.password = password;
		this.favorites = new ArrayList<>();
	}

	public int getAge() {
		return this.age;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public List<Favorite> getFavorites() {
		return this.favorites;
	}
}
