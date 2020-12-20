package jpa.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity{
	@Column
	private String name;

	@Column(nullable = true)
	private int age;

	@Column(nullable = true)
	private String email;

	@Column(nullable = true)
	private String password;

	@OneToMany
	private List<Favorite> favoriteList = new ArrayList<>();

}
