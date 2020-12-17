package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.dto.FavoriteDto;
import jpa.service.FavoriteService;
import jpa.service.MemberService;
import jpa.service.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FavoriteTest {

	@Autowired
	private StationService stationService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private MemberService memberService;

	@Transactional
	@DisplayName("여러개의 즐겨찾기를 사용자에게 저장하는 기능 테스트")
	@Test
	void 여러개_즐겨찾기_사용자_저장_TEST() {
		//given
		stationService.saveStations(Arrays.asList("강남역", "판교역", "양재역", "정자역"));
		memberRepository.save(new Member(30, "goodna17@gmail.com", "password"));

		List<Favorite> favoriteByStation = favoriteService.createFavoriteByStation(
			Arrays.asList(
				new FavoriteDto("강남역", "판교역"),
				new FavoriteDto("양재역", "판교역"),
				new FavoriteDto("강남역", "양재역"),
				new FavoriteDto("강남역", "정자역")));
		String memberEmail = "goodna17@gmail.com";
		memberService.addFavoritesToMember(memberEmail, favoriteByStation);

		//when
		Member memberByEmail = memberRepository.findByEmail(memberEmail);
		//then
		assertThat(memberByEmail.getFavorite()).hasSize(4);
	}

	@DisplayName("같은 역은 즐겨찾기 추가하지 못하는 기능 테스트")
	@Test
	void 즐겨찾기_같은역_추가_제한_TEST() {
		assertThatThrownBy(() -> {
			new FavoriteDto("강남역", "강남역");
		}).isInstanceOf(IllegalArgumentException.class);
	}
}
