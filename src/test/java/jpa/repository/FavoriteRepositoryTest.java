package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Favorite favorite1 = new Favorite(new Member(29));
        Favorite result = favoriteRepository.save(favorite1);
        assertAll(
                () -> assertThat(result.getId()).isNotNull()
        );
    }

    @Test
    void findById() {
        Favorite favorite1 = favoriteRepository.save(new Favorite(new Member(29)));
        Favorite favorite2 = favoriteRepository.findById(favorite1.getId()).get();
        assertThat(favorite1 == favorite2).isTrue();
    }

    @Test
    @DisplayName("즐겨찾기 & 멤버 연관관계 테스트")
    void saveMember() {
        Member member = memberRepository.save(new Member(30));
        memberRepository.flush();
        Favorite favorite1 = favoriteRepository.save(new Favorite(member));
        favoriteRepository.flush();
        assertThat(favorite1.getMember().getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("즐겨찾기 & 역 연관관계 테스트")
    void saveStations() {
        Favorite favorite = favoriteRepository.save(new Favorite(new Member(31)));
        Station fromStation = stationRepository.save(new Station("잠실역"));
        Station toStation = stationRepository.save(new Station("홍대입구역"));
        favorite.addStations(fromStation, toStation);
        Favorite result = favoriteRepository.findById(1L).get();
        assertThat(result.getFromStation().getName()).isEqualTo("잠실역");
        assertThat(result.getToStation().getName()).isEqualTo("홍대입구역");
    }
}
