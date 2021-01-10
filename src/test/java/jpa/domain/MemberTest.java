package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class MemberTest {
	@Autowired
	private StationRepository stations;
	@Autowired
	private MemberRepository members;

	@Test
	void save_test() {
		assertThat(members.save(new Member("abcd@naver.com", "1q2w3e")).getId()).isNotNull();
	}

	@Test
	void addFavorite_test() {
		Station departure = stations.findByName("당산역").orElse(new Station());
		Station destination = stations.findByName("여의도역").orElse(new Station());
		Favorite favorite = new Favorite(departure, destination);
		String email = "abcd@google.com";
		String password = "1q2w3e";

		Member member = members.save(new Member(email, password));
		member.addFavorite(favorite);

		Member actualMember = members.findByEmailAndPassword(email, password).orElse(new Member());
		assertThat(actualMember.getId()).isNotNull();
		assertThat(actualMember.getFavorites())
			.hasSize(1)
			.containsExactly(favorite);
	}

	@Test
	@DisplayName("동일한 즐겨찾기 추가시 Exception이 발생한다.")
	void addFavorite_duplication_test() {
		Station departure = stations.findByName("당산역").orElse(new Station());
		Station destination = stations.findByName("홍대입구역").orElse(new Station());
		Favorite favorite = new Favorite(departure, destination);

		Member member = members.findByEmailAndPassword("qwer@google.com", "1234").orElse(new Member());
		member.addFavorite(favorite);

		assertThatExceptionOfType(DataIntegrityViolationException.class)
			.isThrownBy(() -> members.flush());
	}
}