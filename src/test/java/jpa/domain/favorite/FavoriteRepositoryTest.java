package jpa.domain.favorite;

import jpa.domain.member.Member;
import jpa.domain.member.MemberRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        Member member = Member.builder()
                .age(20)
                .email("pobi@test.com")
                .password("12345")
                .build();

        Station gangnam_station = stationRepository.save(Station.builder()
                .name("강남역")
                .build());
        Station jamsil_station = stationRepository.save(Station.builder()
                .name("잠실역")
                .build());
        Station pangyo_station = stationRepository.save(Station.builder()
                .name("판교역")
                .build());

        favoriteRepository.save(Favorite.builder()
                .departure(gangnam_station)
                .arrival(jamsil_station)
                .member(member)
                .build());
        favoriteRepository.save(Favorite.builder()
                .departure(jamsil_station)
                .arrival(pangyo_station)
                .member(member)
                .build());
    }

    @Test
    @DisplayName("사용자로 Favorite 리스트 조회 테스트")
    void get_favorite_by_member_test() {
        // given
        Member member = memberRepository.findByEmail("pobi@test.com");

        // when
        List<Favorite> favorites = favoriteRepository.findByMember(member);

        // then
        assertAll(
                () -> assertThat(favorites).isNotNull(),
                () -> assertThat(favorites).hasSize(2)
        );
    }

    @Test
    @DisplayName("Favorite 전체 조회 테스트")
    void select_all_favorite_test() {
        // given
        int expected = 2;

        // when
        List<Favorite> actual = favoriteRepository.findAll();

        // then
        assertThat(actual).hasSize(expected);
    }

    @Test
    @DisplayName("Favorite 추가 테스트")
    void insert_favorite_test() {
        // given
        Member member = memberRepository.findByEmail("pobi@test.com");
        Station gangnam_station = stationRepository.findByName("강남역");
        Station jamsil_station = stationRepository.findByName("잠실역");

        Favorite favorite = Favorite.builder()
                .departure(gangnam_station)
                .arrival(jamsil_station)
                .member(member)
                .build();

        // when
        Favorite persistFavorite = favoriteRepository.save(favorite);
        List<Favorite> favorites = favoriteRepository.findAll();

        // then
        assertThat(persistFavorite.getId()).isNotNull();
        assertThat(persistFavorite.getCreatedDate()).isNotNull();
        assertThat(persistFavorite.getModifiedDate()).isNotNull();
        assertThat(favorites.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("사용자가 삭제되면 저장된 Favorite 삭제 테스트")
    void delete_favorite_by_member_test() {
        // given
        Member member = memberRepository.findByEmail("pobi@test.com");

        // when
        memberRepository.delete(member);

        // then
        assertThat(favoriteRepository.findByMember(member)).isEmpty();
    }

}
