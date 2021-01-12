package jpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

	@DisplayName("멤버 생성")
	@Test
	void given_parameters_when_new_member_then_return_member() {
		final String email = "test@test.com";
		final String password = "1q2w3e4r";
		final int age = 24;

		Member member = new Member(email, password, age);

		assertAll(
			() -> assertThat(member).isNotNull(),
			() -> assertThat(member.getEmail()).isEqualTo(email),
			() -> assertThat(member.getPassword()).isEqualTo(password),
			() -> assertThat(member.getAge()).isEqualTo(age)
		);
	}

	@DisplayName("멤버 Favorite 조회")
	@Test
	void given_member_when_get_favorites_then_contains_favorite() {
		Member member = new Member("test@test.com", "1q2w3e4r", 24);
		Station sadang = new Station("사당역");
		Station gangnam = new Station("강남역");

		Favorite favorite = new Favorite(member, sadang, gangnam);

		assertThat(member.getFavorites()).contains(favorite);
	}

}
