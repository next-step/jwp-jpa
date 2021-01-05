package jpa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "member")
@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "age")
	private Integer age;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private final Set<Favorite> favorites = new HashSet<>();

	protected Member() {
	}

	public Member(final String email, final String password, final Integer age) {
		this.email = email;
		this.password = password;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Set<Favorite> getFavorites() {
		return favorites;
	}

	public void addFavorite(final Favorite favorite) {
		this.getFavorites().add(favorite);
	}
}
