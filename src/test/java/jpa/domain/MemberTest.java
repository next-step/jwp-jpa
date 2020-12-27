package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;

@DataJpaTest
class MemberTest {
	@Autowired
	MemberRepository members;

	@DisplayName("Member save 테스트")
	@Test
	public void save() {
		// given
		Member expected = new Member(31, "jpa@google.com", "1234");

		// when
		Member actual = members.save(expected);

		// then
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
			() -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
			() -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
		);
	}

	@DisplayName("Member findByEmail 테스트")
	@Test
	public void findByEmail() {
		// given
		String expected = "jpa@google.com";

		// when
		members.save(new Member(31, "jpa@google.com", "1234"));

		// then
		String actual = members.findByEmail(expected).get(0).getEmail();
		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void update() {
		// given
		Member newMember = new Member(31, "jpa@google.com", "1234");
		Long memberId = members.save(newMember).getId();
		int expected = 40;

		// when
		newMember.setAge(expected);

		// then
		Member findMember = members.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		assertThat(expected).isEqualTo(findMember.getAge());
	}

	@Test
	public void delete() {
		// given
		Member newMember = new Member(31, "jpa@google.com", "1234");
		Long memberId = members.save(newMember).getId();

		// when
		members.delete(newMember);

		// then
		assertThat(members.findById(memberId).isPresent()).isFalse();
	}
}