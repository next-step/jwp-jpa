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

    private void saveFavoriteMember(int age, String email, String password, Favorite favorite) {
        Member member = new Member(age, email, password);
        memberRepository.save(member);

        favorite.setMember(member);
        favoriteRepository.save(favorite);
    }

    private Station saveStation(String name) {
        return stationRepository.save(new Station(name));
    }

    @Test
    @DisplayName("즐겨찾기를 통해 사용자 찾기 테스트")
    void findUserByFavorite() {
        // given
        saveFavoriteMember(30, "white@github.com", "red_white", new Favorite());

        em.clear();

        // when
        List<Favorite> result = favoriteRepository.findAll();

        // then
        for (Favorite favorite : result) {
            assertThat(favorite.getMember()).extracting("age", "email", "password")
                    .containsExactly(30, "white@github.com", "red_white");
        }
    }

    @Test
    @DisplayName("즐겨찾기를 통해 지하철역 찾기 테스트")
    void findFavoriteStationByFavorite() {
        // given
        Favorite favorite = new Favorite();
        FavoriteStation favoriteStation1 = new FavoriteStation(saveStation("왕십리"));
        FavoriteStation favoriteStation2 = new FavoriteStation(saveStation("수원"));
        favorite.addFavoriteStation(favoriteStation1);
        favorite.addFavoriteStation(favoriteStation2);
        saveFavoriteMember(30, "white@github.com", "red_white", favorite);

        em.clear();

        // when
        Favorite findFavorite = favoriteRepository.findById(favorite.getId()).get();

        // then
        assertThat(findFavorite.getFavoriteStations().size()).isEqualTo(2);
        assertThat(findFavorite.getFavoriteStations()).containsExactly(favoriteStation1, favoriteStation2);
        assertThat(findFavorite.getFavoriteStations()).containsExactly(favoriteStation1, favoriteStation2)
                .extracting(f -> f.getStation().getName()).containsExactly("왕십리", "수원");

        // when
        findFavorite.removeFavoriteStation(favoriteStation1);
        em.flush();

        // then
        assertThat(findFavorite.getFavoriteStations().size()).isEqualTo(1);
    }
}
