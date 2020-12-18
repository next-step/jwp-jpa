package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@BeforeEach
	void setUp() {
		Member member = memberRepository.save(new Member(30, "goodna17@gmail.com", "password"));

		Station 강남역 = stationRepository.save(new Station("강남역"));
		Station 판교역 = stationRepository.save(new Station("판교역"));
		Station 양재역 = stationRepository.save(new Station("양재역"));
		Station 정자역 = stationRepository.save(new Station("정자역"));

		favoriteRepository.save(new Favorite(강남역, 판교역, member));
		favoriteRepository.save(new Favorite(양재역, 판교역, member));
		favoriteRepository.save(new Favorite(강남역, 양재역, member));
		favoriteRepository.save(new Favorite(강남역, 정자역, member));
	}

	@DisplayName("사용자 엔티티에서 즐겨찾기 리스트를 가져온다.")
	@Test
	void GET_FAVORITE_TO_USER_ENTITY_TEST() {
		//when
		Member memberByEmail = memberRepository.findByEmail("goodna17@gmail.com");

		assertThat(memberByEmail.getFavorite()).hasSize(4);
	}

	@DisplayName("사용자로 즐겨찾기 리스트를 가져온다.")
	@Test
	void GET_FAVORITE_TO_FAVORITE_ENTITY_BY_USER_TEST() {
		//when
		Member memberByEmail = memberRepository.findByEmail("goodna17@gmail.com");

		//then
		assertThat(favoriteRepository.findByMember(memberByEmail)).hasSize(4);
	}

	@DisplayName("사용자가 삭제된다면 저장된 즐겨찾기를 모두 삭제하는 테스트")
	@Test
	void 사용자_삭제_테스트() {
		//when
		Member memberByEmail = memberRepository.findByEmail("goodna17@gmail.com");
		memberRepository.delete(memberByEmail);

		//then
		assertThat(favoriteRepository.findByMember(memberByEmail)).isEmpty();
	}
}
