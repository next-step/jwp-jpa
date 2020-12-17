package jpa.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;

import jpa.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "age")
	private int age;

	@Column(name = "email", length = 255)
	private String email;

	@Column(name = "password", length = 255)
	private String password;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberFavorite> memberFavorites = new ArrayList<>();

	private Member(int age, String email, String password, List<Favorite> favorites) {
		this.age = age;
		this.email = email;
		this.password = password;
		this.memberFavorites = CollectionUtils.emptyIfNull(favorites).stream()
			.map(favorite -> MemberFavorite.create(this, favorite))
			.collect(Collectors.toList());
	}

	public static Member create(int age, String email, String password) {
		return new Member(age, email, password, null);
	}

	public static Member create(int age, String email, String password, List<Favorite> favorites) {
		return new Member(age, email, password, favorites);
	}

	public Member updateEmail(String email) {
		this.email = email;
		return this;
	}
}