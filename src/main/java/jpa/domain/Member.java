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

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Member member = (Member)o;

		if (id != null ? !id.equals(member.id) : member.id != null)
			return false;
		return email != null ? email.equals(member.email) : member.email == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", age=" + age +
			", email='" + email + '\'' +
			", password='" + password + '\'' +
			", favorites=" + favorites +
			'}';
	}

}
