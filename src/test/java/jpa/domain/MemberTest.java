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

}
