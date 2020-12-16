package jpa.repository;

import jpa.dto.FavoriteDto;
import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import jpa.service.FavoriteService;
import jpa.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class FavoriteTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private MemberService memberService;

	@BeforeEach
	void setUp() {
		stationRepository.save(new Station("강남역"));
		stationRepository.save(new Station("판교역"));
		stationRepository.save(new Station("양재역"));
		stationRepository.save(new Station("정자역"));
		memberRepository.save(new Member(30, "goodna17@gmail.com", "password"));
	}

	@Transactional
	@DisplayName("여러개의 즐겨찾기를 사용자에게 저장하는 기능 테스트")
	@Test
	void 여러개_즐겨찾기_사용자_저장_TEST() {
		List<Favorite> favoriteByStation = favoriteService.createFavoriteByStation(
			Arrays.asList(
				new FavoriteDto("강남역", "판교역"),
				new FavoriteDto("강남역", "판교역"),
				new FavoriteDto("강남역", "정자역")));

		String memberEmail = "goodna17@gmail.com";
		memberService.addFavoritesToMember(memberEmail, favoriteByStation);

		Member memberByEmail = memberService.findMemberByEmail(memberEmail);

		assertThat(memberByEmail.getFavorite().size()).isEqualTo(3);
	}
}
