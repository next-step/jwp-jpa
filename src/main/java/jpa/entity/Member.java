package jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int age;

	private String email;

	private String password;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Favorite> favorites = new ArrayList<>();

	protected Member() {
	}

	public Member(int age, String email, String password) {
		this.age = age;
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}
}