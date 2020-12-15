package jpa.repository;

import jpa.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository members;

	@DisplayName("사용자 저장 테스트")
	@Test
	void save() {
		Member member = new Member(20, "hglee@gmail.com", "12345");

		Member saveMember = members.save(member);

		assertAll(
			() -> assertThat(saveMember.getId()).isNotNull(),
			() -> assertThat(saveMember.getAge()).isEqualTo(member.getAge()),
			() -> assertThat(saveMember.getEmail()).isEqualTo(member.getEmail()),
			() -> assertThat(saveMember.getPassword()).isEqualTo(member.getPassword())
		);
	}

	@DisplayName("사용자 조회 테스트")
	@Test
	void findByEmail() {
		String expectedEmail = "hglee@gmail.com";
		members.save(new Member(20, expectedEmail, "12345"));

		Member findMember = members.findByEmail(expectedEmail);
		assertThat(findMember.getEmail()).isEqualTo(expectedEmail);
		assertThat(findMember.getAge()).isEqualTo(20);
		assertThat(findMember.getPassword()).isEqualTo("12345");
		assertThat(findMember.getCreatedDate()).isBefore(LocalDateTime.now());
		assertThat(findMember.getModifiedDate()).isBefore(LocalDateTime.now());
	}
}
