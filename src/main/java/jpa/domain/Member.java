package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int age;

	@Column
	private String email;

	@Column
	private String password;

	@OneToMany(mappedBy = "member" , orphanRemoval = true)
	private List<Favorite> favorite = new ArrayList<>();

	public Member(int age, String email, String password) {
		this.age = age;
		this.email = email;
		this.password = password;
	}

	protected Member() {
	}

	public void addFavorite(Favorite favorite) {
		this.favorite.add(favorite);
		favorite.setMember(this);
	}

	public boolean isEqualsFavoriteSize(int size) {
		return this.favorite.size() == size;
	}

	public boolean isEqualsEmail(String email) {
		return email.equals(this.email);
	}

	public List<Favorite> getFavorite() {
		return this.favorite;
	}
}
