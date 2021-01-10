package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class FavoriteRepositoryTest {
	@Autowired
	private FavoriteRepository favorites;
	@Autowired
	private MemberRepository members;
	@Autowired
	private StationRepository stations;

	@Test
	@DisplayName("즐겨찾끼가 회원에 추가되지 않았을 경우 저장에 실패한다.")
	void save_ThrowExceptionWhenMemberNotExist_test() {
		Station departure = stations.findByName("당산역").orElse(new Station());
		Station destination = stations.findByName("여의도역").orElse(new Station());

		assertThatExceptionOfType(DataIntegrityViolationException.class)
			.isThrownBy(() -> favorites.save(new Favorite(departure, destination)));
	}

	@Test
	@DisplayName("즐겨찾기가 회원에 추가될 경우 저장에 성공한다.")
	void save_test() {
		Station departure = stations.findByName("당산역").orElse(new Station());
		Station destination = stations.findByName("여의도역").orElse(new Station());
		Favorite favorite = new Favorite(departure, destination);

		Member member = members.save(new Member("1234", "1234"));
		member.addFavorite(favorite);

		members.flush();

		assertThat(favorite.getId()).isNotNull();
	}
}
