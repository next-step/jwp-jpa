package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class FavoriteRepositoryTest {

	@Autowired
	private FavoriteRepository favorites;

	@Autowired
	private EntityManager em;

	private Member member1;

	private Member member2;

	private Station station1;

	private Station station2;

	private Station station3;

	@BeforeEach
	void setUp() {
		member1 = new Member(11, "11", "11");
		member2 = new Member(22, "22", "22");
		em.persist(member1);
		em.persist(member2);

		station1 = persistStation("잠실");
		station2 = persistStation("삼성");
		station3 = persistStation("이태원");
	}

	@Test
	@DisplayName("영속화 되지 않은 Member 를 사용시 예외 확인")
	void save_notPersistMember() {
		Member member = new Member(11, "11", "11");
		Favorite favorite = new Favorite(member, station1, station2);
		assertThatThrownBy(() -> favorites.save(favorite))
				.isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	@Test
	@DisplayName("영속화 된 Member 를 사용시 정상적으로 save 확인")
	void save_persistMember() {
		Member member = new Member(11, "", "");
		em.persist(member);
		favorites.save(new Favorite(member, station1, station2));
	}

	@Test
	@DisplayName("Favorite 에 속한 Station 조회 테스트")
	void findStations() {
		Favorite favorite = new Favorite(member1, station1, station2);
		em.persist(favorite);

		assertThat(favorites.findById(favorite.getId())).isPresent()
				.get()
				.satisfies(f -> assertThat(f.getDepartureStation()).isEqualTo(station1))
				.satisfies(f -> assertThat(f.getArrivalStation()).isEqualTo(station2));
	}

	@Test
	@DisplayName("멤버가 같은 favorite find")
	void findAllByMember() {
		Favorite favorite1 = new Favorite(member1, station1, station2);
		Favorite favorite2 = new Favorite(member1, station2, station3);
		Favorite favorite3_otherMember = new Favorite(member2, station2, station3);
		em.persist(favorite1);
		em.persist(favorite2);
		em.persist(favorite3_otherMember);

		assertThat(favorites.findAllByMember(member1)).hasSize(2)
				.containsExactly(favorite1, favorite2);
	}

	@Test
	@DisplayName("arrivalStation 이 같은 favorite find")
	void findAllByDepartureStation() {
		Favorite favorite1 = new Favorite(member1, station1, station2);
		Favorite favorite2 = new Favorite(member1, station1, station3);
		Favorite favorite3_otherStation = new Favorite(member1, station2, station3);
		em.persist(favorite1);
		em.persist(favorite2);
		em.persist(favorite3_otherStation);

		assertThat(favorites.findAllByDepartureStation(station1)).hasSize(2)
				.containsExactly(favorite1, favorite2);
	}

	private Station persistStation(String name) {
		Station station = new Station(name);
		em.persist(station);
		return station;
	}
}
