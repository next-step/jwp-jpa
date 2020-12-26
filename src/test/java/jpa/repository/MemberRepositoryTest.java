package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import jpa.domain.StationLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Member expected = new Member();
        Member actual = memberRepository.save(expected);
        assertAll(
                ()->assertNotNull(actual.getId())
        );
    }

    @Test
    void findByAge() {
        String expected = "27";
        Member member = new Member();
        member.setAge(expected);
        memberRepository.save(member);
        String actual = memberRepository.findByAge(expected).getAge();
        assertEquals(actual, expected);
    }

    @Test
    void findByEmail() {
        String expected = "jack@kakao.com";
        Member member = new Member();
        member.setEmail(expected);
        memberRepository.save(member);
        String actual = memberRepository.findByEmail(expected).getEmail();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("즐겨찾기 조회하는 테스트")
    void findByFavorites() {
        Member member = memberRepository.save(new Member("jack"));
        Station start = stationRepository.save(new Station("사당"));
        Station end = stationRepository.save(new Station("강남역"));

        favoriteRepository.save(new Favorite(start, end, member));

        Favorite favorites = member.getFavorites().get(0);
        assertAll(
                ()->assertEquals(favorites.getStartStation(), start),
                ()->assertEquals(favorites.getEndStation(), end)
        );
    }
}