package jpa.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import jpa.common.BaseTime;
import jpa.favorite.Favorite;
import lombok.Getter;

@Entity
public class Member extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Column
	private int age;

	@Getter
	@Column
	private String email;

	@Column
	private String password;

	@Getter
	@OneToMany
	@JoinColumn
	private final List<Favorite> favorites = new ArrayList<>();

	public void addFavorite(Favorite favorite) {
		this.favorites.add(favorite);
	}
}
