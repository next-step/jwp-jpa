package jpa.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import jpa.common.BaseTime;
import jpa.favorite.Favorite;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int age;
	private String email;
	private String password;
	@OneToMany(mappedBy = "member")
	private final List<Favorite> favorites = new ArrayList<>();

	public void addFavorite(Favorite favorite) {
		this.favorites.add(favorite);
	}
}
