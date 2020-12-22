package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Favorite;
import jpa.domain.Member;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
@DataJpaTest
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository members;

	@Test
	void save() {
		String email = "email@email.com";
		String password = "pwd";
		int age = 30;

		Member actual = members.save(new Member(email, password, age));
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(email),
			() -> assertThat(actual.getPassword()).isEqualTo(password),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByEmail() {
		String email = "email@email.com";
		String password = "pwd";
		int age = 30;

		members.save(new Member(email, password, age));

		Member actual = members.findByEmail(email);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(email),
			() -> assertThat(actual.getPassword()).isEqualTo(password),
			() -> assertThat(actual.getAge()).isEqualTo(age)
		);
	}

	@Test
	void update() {
		String email = "email@email.com";
		String password = "pwd";
		int age = 30;

		Member savedMember = members.save(new Member(email, password, age));

		String expectedPassword = "changePwd";
		int expectedAge = 40;
		savedMember.changePassword(expectedPassword);
		savedMember.changeAge(expectedAge);

		Member actual = members.findByEmail(email);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(email),
			() -> assertThat(actual.getPassword()).isEqualTo(expectedPassword),
			() -> assertThat(actual.getAge()).isEqualTo(expectedAge)
		);
	}

	@Test
	void delete() {
		String email = "email@email.com";
		String password = "pwd";
		int age = 30;

		Member savedMember = members.save(new Member(email, password, age));

		members.delete(savedMember);
		Member actual = members.findByEmail(email);

		assertThat(actual).isNull();
	}

	@Test
	void memberHasAnyFavorites() {
		String email = "email@email.com";
		String password = "pwd";
		int age = 30;

		Member savedMember = members.save(new Member(email, password, age));

		Favorite favorite = new Favorite();
		savedMember.addFavorites(favorite);

		Member actual = members.findByEmail(savedMember.getEmail());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(email),
			() -> assertThat(actual.getPassword()).isEqualTo(password),
			() -> assertThat(actual.getAge()).isEqualTo(age),
			() -> assertThat(actual.getFavorites()).contains(favorite)
		);

	}
}
