package jpa.domain.favorite;

import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class FavoriteTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("즐겨찾기를 통해 사용자 찾기 테스트")
    void findUserByFavorite() {
        // given
        Member member = saveMember(30, "white@github.com", "red_white");
        favoriteRepository.save(new Favorite(member, saveStation("인천"), saveStation("청량리")));

        // when
        em.clear();
        List<Favorite> result = favoriteRepository.findAll();

        // then
        for (Favorite favorite : result) {
            assertThat(favorite.getMember()).extracting("age", "email", "password")
                    .containsExactly(30, "white@github.com", "red_white");
        }
    }

    @Test
    @DisplayName("즐겨찾기를 통해 지하철역 찾기 테스트")
    void findStationByFavorite() {
        // given
        Member member = saveMember(30, "white@github.com", "red_white");
        Favorite favorite = favoriteRepository.save(new Favorite(member, saveStation("인천"), saveStation("청량리")));
        favorite.changeArrivalStation(saveStation("왕십리"));

        // when
        em.flush();
        em.clear();
        Favorite findFavorite = favoriteRepository.findById(favorite.getId()).get();

        // then
        assertThat(findFavorite.getDepartureStation().getName()).isEqualTo("인천");
        assertThat(findFavorite.getArrivalStation().getName()).isEqualTo("왕십리");
    }

    private Member saveMember(int age, String email, String password) {
        return memberRepository.save(new Member(age, email, password));
    }

    private Station saveStation(String name) {
        return stationRepository.save(new Station(name));
    }
}
