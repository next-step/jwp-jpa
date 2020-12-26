package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberTest {

	@Autowired
	private EntityManager em;

	private Member member;

	private Station station1;
	private Station station2;
	private Station station3;

	@BeforeEach
	void setUp() {
		member = new Member(11, "", "");
		station1 = new Station("삼성");
		station2 = new Station("잠실");
		station3 = new Station("선릉");
		em.persist(member);
		em.persist(station1);
		em.persist(station2);
		em.persist(station3);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void removeFavorite(boolean clear) {
		// given
		Favorite favorite1 = new Favorite(member, station1, station2);
		Favorite favorite2 = new Favorite(member, station2, station1);
		Favorite favorite3 = new Favorite(member, station1, station3);
		Favorite favorite4 = new Favorite(member, station3, station1);
		em.persist(favorite1);
		em.persist(favorite2);
		em.persist(favorite3);
		em.persist(favorite4);
		em.flush();

		// when
		member.removeFavorite(favorite1);
		em.flush();
		if (clear) em.clear();

		// then
		assertThat(em.find(Member.class, member.getId()).getFavorites())
				.hasSize(3)
				.containsExactly(favorite2, favorite3, favorite4);
	}

}
