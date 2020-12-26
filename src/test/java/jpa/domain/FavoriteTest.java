package jpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteTest {

	@Autowired
	private EntityManager em;

	@Test
	void changeMember() {
		Member member1 = new Member(11, "", "");
		Member member2 = new Member(22, "", "");
		em.persist(member1);
		em.persist(member2);

		Favorite favorite = new Favorite(member1, persistStation("a"), persistStation("b"));
		em.persist(favorite);

		favorite.changeMember(member2);
		em.flush();

		Favorite favorite2 = em.find(Favorite.class, favorite.getId());
		assertThat(member1.getFavorites()).doesNotContain(favorite);
		assertThat(member2.getFavorites()).contains(favorite);
	}

	private Station persistStation(String name) {
		Station station = new Station(name);
		em.persist(station);
		return station;
	}
}
