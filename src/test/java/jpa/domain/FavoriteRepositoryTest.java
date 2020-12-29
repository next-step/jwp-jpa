package jpa.domain;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Station 강남_station = stationRepository.save(new Station("강남"));
        Station 방배_station = stationRepository.save(new Station("방배"));
        Station 잠실_station = stationRepository.save(new Station("잠실"));

        Member member = memberRepository.save(new Member(24, "asd@gmail.com", "123"));

        Favorite favorite1 = favoriteRepository.save(new Favorite(강남_station, 방배_station));
        Favorite favorite2 = favoriteRepository.save(new Favorite(잠실_station, 강남_station));
        member.addFavorite(favorite1);
        member.addFavorite(favorite2);
    }

    @DisplayName("사용자는 여러 즐겨찾기를 가질 수 있다.")
    @Test
    void find_All_Favorites() {
        Member member = memberRepository.findByEmail("asd@gmail.com");
        assertThat(member.getFavorites()).hasSize(2);
    }

    @DisplayName("즐겨찾기에 출발역과 도착역이 포함되어 있다.")
    @Test
    void check_Departure_Station() {
        List<Favorite> favorites = memberRepository.findByEmail("asd@gmail.com").getFavorites();
        favorites.forEach(favorite -> {
            assertThat(favorite.getDepartureStation()).isNotNull();
            assertThat(favorite.getArrivalStation()).isNotNull();
        });
    }
}
