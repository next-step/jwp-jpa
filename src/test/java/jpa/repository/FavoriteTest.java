package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import jpa.dto.FavoriteDto;
import jpa.service.FavoriteService;
import jpa.service.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FavoriteTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private FavoriteRepository favoriteRepository;

	private void given() {
		stationRepository.save(new Station("강남역"));
		stationRepository.save(new Station("판교역"));
		stationRepository.save(new Station("양재역"));
		stationRepository.save(new Station("정자역"));

		Member member = memberRepository.save(new Member(30, "goodna17@gmail.com", "password"));

		List<Favorite> favoriteByStation = favoriteService.createFavoriteByStation(
			Arrays.asList(
				new FavoriteDto("강남역", "판교역"),
				new FavoriteDto("양재역", "판교역"),
				new FavoriteDto("강남역", "양재역"),
				new FavoriteDto("강남역", "정자역")));
		favoriteByStation.forEach(member::addFavorite);
	}

	@Transactional
	@DisplayName("Favorite 에서 사용자에 해당하는 즐겨찾기를 가져온다.")
	@Test
	void 즐겨찾기에서_사용자에_해당하는_데이터를_가져온_TEST() {
		//given
		given();

		//when
		Member memberByEmail = memberRepository.findByEmail("goodna17@gmail.com");

		List<Favorite> byMember = favoriteRepository.findByMember(memberByEmail);

		assertThat(byMember).hasSize(4);

	}

	@Transactional
	@DisplayName("사용자에서 즐겨찾기를 가져온다")
	@Test
	void 여러개_즐겨찾기_사용자_저장_TEST() {
		//given
		given();

		//when
		Member memberByEmail = memberRepository.findByEmail("goodna17@gmail.com");

		//then
		assertThat(memberByEmail.getFavorite()).hasSize(4);
	}

	@Transactional
	@DisplayName("사용자가 삭제된다면 저장된 즐겨찾기를 모두 삭제하는 테스트")
	@Test
	void 사용자_삭제_테스트() {
		//given
		given();

		//when
		Member memberByEmail = memberRepository.findByEmail("goodna17@gmail.com");
		memberRepository.delete(memberByEmail);

		//then
		assertThat(favoriteRepository.findByMember(memberByEmail)).isEmpty();
	}

	@DisplayName("같은 역은 즐겨찾기 추가하지 못하는 기능 테스트")
	@Test
	void 즐겨찾기_같은역_추가_제한_TEST() {
		assertThatThrownBy(() -> {
			new FavoriteDto("강남역", "강남역");
		}).isInstanceOf(IllegalArgumentException.class);
	}
}
