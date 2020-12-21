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

	private Member presetMember;
	@BeforeEach
	void setUp() {
		presetMember = new Member("email@email.com", "pwd", 30);
	}

	@Test
	void save() {
		Member actual = members.save(presetMember);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(presetMember.getEmail()),
			() -> assertThat(actual.getPassword()).isEqualTo(presetMember.getPassword()),
			() -> assertThat(actual.getCreateDate()).isNotNull(),
			() -> assertThat(actual.getModifiedDate()).isNotNull()
		);
	}

	@Test
	void findByEmail() {
		Member savedMember = members.save(presetMember);

		Member actual = members.findByEmail(savedMember.getEmail());
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(presetMember.getEmail()),
			() -> assertThat(actual.getPassword()).isEqualTo(presetMember.getPassword()),
			() -> assertThat(actual.getAge()).isEqualTo(presetMember.getAge())
		);
	}

	@Test
	void update() {
		Member savedMember = members.save(presetMember);

		String expectedPassword = "changePwd";
		int expectedAge = 40;
		savedMember.changePassword(expectedPassword);
		savedMember.changeAge(expectedAge);

		Member actual = members.findByEmail(savedMember.getEmail());
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(savedMember.getEmail()),
			() -> assertThat(actual.getPassword()).isEqualTo(expectedPassword),
			() -> assertThat(actual.getAge()).isEqualTo(expectedAge)
		);
	}

	@Test
	void delete() {
		Member savedMember = members.save(presetMember);

		members.delete(savedMember);
		Member actual = members.findByEmail(presetMember.getEmail());

		assertThat(actual).isNull();
	}

	@Test
	void memberHasAnyFavorites() {
		Member savedMember = members.save(presetMember);

		Favorite favorite = new Favorite();
		savedMember.addFavorites(favorite);

		Member actual = members.findByEmail(savedMember.getEmail());

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(savedMember.getEmail()),
			() -> assertThat(actual.getAge()).isEqualTo(savedMember.getAge()),
			() -> assertThat(actual.getFavorites()).contains(favorite)
		);

	}
}
