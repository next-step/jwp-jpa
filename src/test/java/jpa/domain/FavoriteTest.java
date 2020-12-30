package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.repository.FavoriteRepository;
import jpa.repository.MemberRepository;
import jpa.repository.StationRepository;

@DataJpaTest
public class FavoriteTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	private Member member;

	private Station sadang;

	private Station gangnam;

	@BeforeEach
	public void setup() {
		Member member = new Member("test@test.com", "1234", 32);
		this.member = memberRepository.saveAndFlush(member);

		Station sadang = new Station("사당역");
		Station gangnam = new Station("강남역");
		this.sadang = stationRepository.saveAndFlush(sadang);
		this.gangnam = stationRepository.saveAndFlush(gangnam);
	}

	@DisplayName("Favorite 생성")
	@Test
	void given_favorite_when_save_then_return_created_favorite() {
		Favorite favorite = new Favorite(member, sadang, gangnam);

		Favorite actual = favoriteRepository.save(favorite);

		assertThat(actual.getId()).isNotNull();
	}

	@DisplayName("Favorite 조회")
	@Test
	void given_favorite_when_save_and_findById_then_return_created_favorite() {
		Favorite favorite = new Favorite(member, sadang, gangnam);
		Favorite createdFavorite = favoriteRepository.save(favorite);

		Favorite actual = favoriteRepository.findById(1L)
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(actual.getId()).isEqualTo(createdFavorite.getId()),
			() -> assertThat(actual == favorite).isTrue()
		);
	}

	@DisplayName("사용자를 조회시 Favorite 조회")
	@Test
	void given_favorite_when_member_find_then_has_favorite() {
		Favorite favorite = new Favorite(member, sadang, gangnam);
		Favorite createdFavorite = favoriteRepository.save(favorite);

		Member member = memberRepository.findByEmail("test@test.com")
			.orElseThrow(IllegalArgumentException::new);

		assertThat(member.getFavorites()).contains(createdFavorite);

	}

	@DisplayName("즐겨찾기 조회시 사용자 확인")
	@Test
	void given_member_when_favorite_find_then_equal_member() {

		Favorite favorite = new Favorite(member, sadang, gangnam);
		Favorite createdFavorite = favoriteRepository.save(favorite);

		assertThat(createdFavorite.getMember()).isEqualTo(member);

	}

	@DisplayName("즐겨찾기 조회시 출발역/도착역 확인")
	@Test
	void given_favorite_when_favorite_find_then_has_station() {

		Favorite favorite = new Favorite(member, sadang, gangnam);
		Favorite createdFavorite = favoriteRepository.save(favorite);

		assertAll(
			() -> assertThat(createdFavorite.getDepartureStation()).isNotNull(),
			() -> assertThat(createdFavorite.getDepartureStation()).isEqualTo(sadang),
			() -> assertThat(createdFavorite.getArrivalStation()).isNotNull(),
			() -> assertThat(createdFavorite.getArrivalStation()).isEqualTo(gangnam)
		);

	}

}
