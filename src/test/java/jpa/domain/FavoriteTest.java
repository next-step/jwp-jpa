package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FavoriteTest {

	@DisplayName("생성한 즐겨찾기의 멤버가 등록시 멤버랑 동일")
	@Test
	void given_member_station_when_new_then_favorite_member_equals_member() {
		Member member = new Member("test@test.com", "1q2w3e4r", 24);
		Station sadang = new Station("사당역");
		Station gangnam = new Station("강남역");

		Favorite favorite = new Favorite(member, sadang, gangnam);

		assertThat(favorite.getMember()).isEqualTo(member);
	}

}
