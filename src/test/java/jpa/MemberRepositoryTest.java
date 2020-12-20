package jpa;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import jpa.repository.MemberRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Test
	void 사용자는_여러_즐겨찾기를_가질_수_있다(){
		Favorite favorite1 = new Favorite(new Station("왕십리역"), new Station("강남역"));
		Favorite favorite2 = new Favorite(new Station("답십리역"), new Station("광화문역"));

		List<Favorite> favorites = new ArrayList<>();
		favorites.add(favorite1);
		favorites.add(favorite2);
		Member member = new Member("홍길동", 30, "gildong.hong@gmail.com", "ghdrlfehd1234", favorites);

		memberRepository.save(member);

		Member findMember = memberRepository.findByName("홍길동");

		assertAll(
				() -> assertThat(findMember.getFavoriteList().contains(favorite1)),
				() -> assertThat(findMember.getFavoriteList().contains(favorite2))
		);



	}


}
