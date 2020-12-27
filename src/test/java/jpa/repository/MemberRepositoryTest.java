package jpa.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.entity.Member;

@DisplayName("MemberRepositoryTest : select 쿼리를 다양하게 날려보고 결과값도 다양하게 받아보는 테스트")
@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	EntityManager em;

	@Autowired
	private MemberRepository memberRepository;

	/**
	 * CRUD 테스트를 위한 기본적인 rows를 insert
	 */
	@BeforeEach
	void saveBefaoreEach() {
		memberRepository.saveAll(Arrays.asList(
			new Member(29, "abc@gmail.com", "1234"),
			new Member(30, "123@gmail.com", "q1w2e3"),
			new Member(31, "qwer@gmail.com", "password"),
			new Member(32, "asd@hotmail.com", "imsi")
		));
		em.flush();
	}

	@DisplayName("select : 복잡한 쿼리 메소드를 사용해보는 테스트 1")
	@Test
	public void select1() {
		assertThat(memberRepository.findByEmailContaining("gmail"))
			.hasSize(3)
			.allSatisfy(member -> assertThat(member.getEmail()).contains("gmail"));
	}

	@DisplayName("select : 복잡한 쿼리 메소드를 사용해보는 테스트 2")
	@Test
	public void select2() {
		List<Member> members = memberRepository.findTop2ByOrderByAgeDesc();
		assertThat(members).hasSize(2);
		assertThat(members.get(0).getEmail()).isEqualTo("asd@hotmail.com");
		assertThat(members.get(1).getEmail()).isEqualTo("qwer@gmail.com");
	}

	@DisplayName("select : java8의 Stream 방식으로 쿼리 결과를 전달받음")
	@Test
	public void select3() {
		try (Stream<Member> memberAll = memberRepository.findAllByOrderByAgeDesc()) {
			List<String> nameList = memberAll
				.map(Member::getEmail)
				.collect(Collectors.toList());

			assertThat(nameList)
				.hasSize(4)
				.containsSequence("asd@hotmail.com", "qwer@gmail.com", "123@gmail.com", "abc@gmail.com");
		}
	}

	@DisplayName("update : 한번에 update를 진행함. 단 영속성 컨텍스트와 별개로 진행되므로 flush와 clear를 강제로 해줘야함")
	@Test
	public void update() {
		Member hotmailMember = memberRepository.findByEmailContaining("hotmail").get(0);
		assertThat(hotmailMember.getAge()).isEqualTo(32);

		int resultCount = memberRepository.updateAllAgePlus(2);
		assertThat(resultCount).isEqualTo(4);

		Member hotmailMember2 = memberRepository.findByEmailContaining("hotmail").get(0);
		assertThat(hotmailMember2.getAge()).isEqualTo(34);
	}

	@DisplayName("delete : 영속성 컨텍스트의 lifecycle대로 움직이는 방식으로 delete를 진행. 단, delete를 한개 씩 진행함")
	@Test
	public void delete() {
		assertThat(memberRepository.findAll().size()).isEqualTo(4);
		memberRepository.deleteByEmailContaining("gmail");
		assertThat(memberRepository.findAll().size()).isEqualTo(1);
	}

	@DisplayName("delete : 한번에 delete를 진행함. 단 영속성 컨텍스트와 별개로 진행되므로 flush와 clear를 강제로 해줘야함")
	@Test
	public void deleteBulk() {
		assertThat(memberRepository.findAll().size()).isEqualTo(4);
		memberRepository.deleteInBulkByEmailContaining("gmail");
		assertThat(memberRepository.findAll().size()).isEqualTo(1);
	}
}