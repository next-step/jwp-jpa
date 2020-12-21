package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@Entity
public class Member extends BaseEntity {

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

	@OneToMany
	@JoinColumn(name = "member_id")
	private List<Favorite> favorites = new ArrayList<>();

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getAge() {
		return age;
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public void changeAge(int age) {
		this.age = age;
	}

	public void addFavorites(Favorite favorite) {
		favorites.add(favorite);
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}
}
