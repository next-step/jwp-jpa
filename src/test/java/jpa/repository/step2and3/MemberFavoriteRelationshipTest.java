package jpa.repository.step2and3;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.repository.FavoriteRepository;
import jpa.repository.MemberRepository;

@DisplayName("member와 favorite이라는 one to many 연관관계에 대한 CRUD 학습 테스트")
public class MemberFavoriteRelationshipTest extends BeforeEachTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@DisplayName("select : 사용자만 select를 해도, 사용자가 가지는 즐겨찾기 목록을 불러올 수 있다")
	@Test
	void selectMember() {
		Member member = memberRepository.findByEmail("123@gmail.com");
		assertThat(member.getFavorites()).hasSize(2);
	}

	@DisplayName("select : 즐겨찾기에서도 many to one 관계를 통해 사용자를 바로 얻을 수 있다")
	@Test
	void selectFavorite() {
		Member member = memberRepository.findByEmail("123@gmail.com");
		List<Favorite> favoriteList = favoriteRepository.findAllByMember(member);
		assertThat(favoriteList)
			.hasSize(2)
			.allSatisfy(favorite -> assertThat(favorite.getMember()).isEqualTo(member));
	}

	@DisplayName("delete : CascadeType.ALL 옵션 덕분에 사용자를 지워도 즐겨찾기가 함께 지워지는 것을 확인한다")
	@Test
	void deleteMember() {
		// CascadeType.ALL을 걸어두었기에 Favorite이 함께 지워진다
		memberRepository.deleteByEmailContaining("123@gmail.com");

		Member member = memberRepository.findByEmail("123@gmail.com");
		assertThat(favoriteRepository.findAllByMember(member)).hasSize(0);
	}

	@DisplayName("delete : 사용자의 즐겨찾기 목록에서 remove한다고 DB에서 delete되지 않음을 확인한다.")
	@Test
	void deleteMemberFavorites() {
		Member member = memberRepository.findByEmail("123@gmail.com");
		member.getFavorites().removeIf(favorite -> true); // orphanRemoval=true 옵션을 걸지 않았기에 delete 진행 안된다

		assertThat(favoriteRepository.findAllByMember(member)).hasSize(2);

		em.flush();
		em.clear();

		member = memberRepository.findByEmail("123@gmail.com");
		for (Favorite favorite : member.getFavorites()) {
			favoriteRepository.delete(favorite);
		}
		member.getFavorites().removeIf(favorite -> true); // 이번엔 미리 delete를 해 두었기 때문에 정상적으로 동작한다
		assertThat(favoriteRepository.findAllByMember(member)).hasSize(0);
	}
}