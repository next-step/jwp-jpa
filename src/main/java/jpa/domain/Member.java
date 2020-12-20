package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Member extends BaseEntity{

	private int age;

	private String email;

	private String password;

	@OneToMany
	@JoinColumn(name = "member_id")
	private List<Favorite> favorites = new ArrayList<>();

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

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void changeEmail(String email) {
		this.email = email;
	}

	public void addFavorite(Favorite favorite) {
		this.favorites.add(favorite);
	}
}
