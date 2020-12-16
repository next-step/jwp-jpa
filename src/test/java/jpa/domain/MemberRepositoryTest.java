package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import jpa.domain.entity.Member;
import jpa.domain.repository.MemberRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setup() {
		memberRepository.save(new Member(20, "test1@test.com", "12345"));
		memberRepository.save(new Member(30, "test2@test.com", "abcdef"));
	}

	@DisplayName("단일 조회 테스트")
	@Test
	void findById() {
		Member actual = memberRepository.findById(1L).get();
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.getId()).isNotNull()
		);
	}

	@DisplayName("전체 조회 테스트")
	@Test
	void findAll() {
		int expectedLength = 2;

		List<Member> actualAll = memberRepository.findAll();

		assertThat(actualAll).hasSize(expectedLength);
	}

	@Test
	@DisplayName("insert 테스트")
	void insert() {
		int expectedLength = 3;

		Member newMember = new Member();
		memberRepository.save(newMember);
		List<Member> actualAll = memberRepository.findAll();

		assertThat(actualAll).hasSize(expectedLength);
	}

	@Test
	@DisplayName("update 테스트")
	void update() {
		String newEmail = "test3@test.com";
		Long findId = 1L;

		Member beforeMember = memberRepository.getOne(findId);
		beforeMember.updateEmail(newEmail);
		memberRepository.flush();
		Member afterMember = memberRepository.getOne(findId);

		assertAll(
			() -> assertThat(afterMember.getId()).isEqualTo(beforeMember.getId()),
			() -> assertThat(afterMember.getEmail()).isEqualTo(beforeMember.getEmail())
		);
	}

	@Test
	@DisplayName("delete 테스트")
	void delete() {
		int expectedLength = 1;

		Member member = memberRepository.getOne(1L);
		memberRepository.delete(member);
		List<Member> actualAll = memberRepository.findAll();

		assertThat(actualAll).hasSize(expectedLength);
	}
}