package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository members;

	@Autowired
	private EntityManager em;

	@Test
	void save() {
		final int age = 59;
		final String email = "tearightss@gmail.com";
		final String password = "secret";
		Member member1 = new Member(age, email, password);
		member1 = members.save(member1);

		assertThat(members.findById(member1.getId()))
				.isPresent()
				.get()
				.satisfies(member -> assertThat(member.getAge()).isEqualTo(age))
				.satisfies(member -> assertThat(member.getEmail()).isEqualTo(email))
				.satisfies(member -> assertThat(member.getPassword()).isEqualTo(password));
	}

	@Test
	@DisplayName("createdDate, ModifiedDate 잘 저장되는지 확인")
	void auditing() {
		Member member = members.save(new Member(29, "tearightss@gmail.com", "secret"));

		assertThat(member.getCreatedDate()).isNotNull();
		assertThat(member.getModifiedDate()).isNotNull();
	}

	@Test
	void findAllByAge() {
		members.save(createMember(25));
		members.save(createMember(25));
		members.save(createMember(26));
		members.save(createMember(27));

		assertThat(members.findAllByAge(25)).hasSize(2);
		assertThat(members.findAllByAge(26)).hasSize(1);
		assertThat(members.findAllByAge(27)).hasSize(1);
	}

	@Test
	void findAllByAgeGreaterThanEqual() {
		members.save(createMember(25));
		members.save(createMember(25));
		members.save(createMember(26));
		members.save(createMember(27));

		assertThat(members.findAllByAgeGreaterThanEqual(25)).hasSize(4);
		assertThat(members.findAllByAgeGreaterThanEqual(26)).hasSize(2);
		assertThat(members.findAllByAgeGreaterThanEqual(27)).hasSize(1);
	}

	private Member createMember(int age) {
		return new Member(age, "abc", "secret");
	}

	@Test
	void findAllByEmailContains() {
		members.save(createMember("tearight@gmail.com"));
		members.save(createMember("tearight@github.com"));
		members.save(createMember("tearight@woowa.com"));
		members.save(createMember("superman@gmail.com"));

		assertThat(members.findAllByEmailContains("tearight")).hasSize(3);
		assertThat(members.findAllByEmailContains("@gmail.com")).hasSize(2);
	}

	private Member createMember(String email) {
		return new Member(22, email, "secret");
	}

	@Test
	@DisplayName("연관관계로 등록된 Favorite 를 역그래프 탐색가능한지 테스트")
	void hasFavorites() {
		Member member = new Member(22, "", "");
		Favorite favorite1 = new Favorite(member, persistStation("이태원"), persistStation("잠실"));
		Favorite favorite2 = new Favorite(member, persistStation("동대문"), persistStation("모란"));
		em.persist(member);
		em.persist(favorite1);
		em.persist(favorite2);
		em.flush();

		member = members.findById(member.getId()).get();
		assertThat(member.getFavorites())
				.hasSize(2)
				.containsExactly(favorite1, favorite2);
	}

	@Test
	@DisplayName("연관관계로 등록된 Favorite 를 detach 된 상태에서도 역그래프 탐색가능한지 테스트")
	void hasFavorites_emClear() {
		Member member = new Member(22, "", "");
		Favorite favorite1 = new Favorite(member, persistStation("이태원"), persistStation("잠실"));
		Favorite favorite2 = new Favorite(member, persistStation("동대문"), persistStation("모란"));
		em.persist(member);
		em.persist(favorite1);
		em.persist(favorite2);
		em.flush();
		em.clear();

		member = members.findById(member.getId()).get();
		assertThat(member.getFavorites())
				.hasSize(2)
				.containsExactly(favorite1, favorite2);
	}

	private Station persistStation(String name) {
		Station station = new Station(name);
		em.persist(station);
		return station;
	}

}
