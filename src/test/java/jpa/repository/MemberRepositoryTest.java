package jpa.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Member;

@DataJpaTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("Member 등록")
	@Test
	void given_member_when_save_then_return_created_member() {
		Member member = new Member("leemingyu05@gmail.com", "1q2w3e4r", 33);

		Member actual = memberRepository.save(member);

		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getEmail()).isEqualTo(member.getEmail())
		);
	}

	@DisplayName("Member 조회")
	@Test
	void given_member_when_save_and_findByEmail_then_return_created_member() {
		Member member = new Member("leemingyu05@gmail.com", "1q2w3e4r", 33);
		memberRepository.save(member);

		Member actual = memberRepository.findByEmail(member.getEmail())
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(actual.getEmail()).isEqualTo(member.getEmail()),
			() -> assertThat(actual == member).isTrue()
		);
	}

}
