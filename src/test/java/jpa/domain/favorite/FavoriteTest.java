package jpa.domain.favorite;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;

@DataJpaTest
class FavoriteTest {
	@Autowired
	EntityManager em;

	@Autowired
	private FavoriteRepository favorites;

	@Autowired
	private MemberRepository members;

	@Autowired
	private StationRepository stations;

	@Test
	public void save() {
		// given
		String expectedEmail = "jpa@google.com";
		String expectedStationOne = "강남역";
		String expectedStationTwo = "잠실역";
		Member member = new Member(31, expectedEmail, "1234");
		Station stationOne = new Station(expectedStationOne);
		Station stationTwo = new Station(expectedStationTwo);
		Favorite favorite = new Favorite(member, stationOne, stationTwo);

		// when
		members.save(member);
		stations.save(stationOne);
		stations.save(stationTwo);
		Long favoriteId = favorites.save(favorite).getId();

		em.flush();
		em.clear();

		// then
		Favorite findFavorite = favorites.findById(favoriteId)
			.orElseThrow(() -> new IllegalArgumentException("아이디에 해당하는 데이터가 없습니다."));
		assertThat(expectedEmail).isEqualTo(findFavorite.getMember().getEmail());
		assertThat(expectedStationOne).isEqualTo(findFavorite.getDepartureStation().getName());
		assertThat(expectedStationTwo).isEqualTo(findFavorite.getArrivalStation().getName());
	}
}
