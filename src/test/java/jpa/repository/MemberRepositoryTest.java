package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
		Member expected = new Member("email@email.com", "pwd", 30);

		Member actual = members.save(expected);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
			() -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
		);
	}

	@Test
	void findByEmail() {
		String expectedEmail = "email@email.com";
		String expectedPassword = "pwd";
		int expectedAge = 30;

		members.save(new Member(expectedEmail, expectedPassword, expectedAge));

		Member actual = members.findByEmail(expectedEmail);
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(expectedEmail),
			() -> assertThat(actual.getPassword()).isEqualTo(expectedPassword),
			() -> assertThat(actual.getAge()).isEqualTo(expectedAge)
		);
	}

}
