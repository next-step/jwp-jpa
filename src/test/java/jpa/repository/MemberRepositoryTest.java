package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void create() {
        // given
        Member newMember = Member.builder().age(20).email("chh9975").password("1234").build();

        // when
        Member savedMember = memberRepository.save(newMember);

        // then
        assertAll(
                () -> assertThat(savedMember).isEqualTo(newMember),
                () -> assertThat(savedMember.getId()).isNotNull()
        );
    }

    @Test
    void Member_Favorite_추가() {
        // given
        Member member = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        Station 당산역 = Station.builder().name("당산역").build();
        Station 강남역 = Station.builder().name("강남역").build();
        Station 잠실역 = Station.builder().name("잠실역").build();

        // when
        member.addFavorite(강남역, 당산역);
        member.addFavorite(강남역, 잠실역);

        // then
        assertThat(member.getFavorites()).hasSize(2);
    }

    @Test
    void delete() {
        // given
        Member expected = memberRepository.save(Member.builder()
                .age(30).email("hyehwanchoi").password("1234").build());

        memberRepository.delete(expected);

        // when
        Member deletedMember = memberRepository.findByEmail("hyehwanchoi");

        // then
        assertThat(deletedMember).isNull();
    }

    @Test
    void update() {
        // given
        Member newMember = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        newMember.changeEmail("nextstep.com");

        // when
        Member findMember = memberRepository.findByEmail("nextstep.com");

        // then
        assertThat(findMember.getEmail()).isEqualTo("nextstep.com");
    }

    @Test
    void deleteFavorite() {
        // given
        Member newMember = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        Station 당산역 = stationRepository.save(Station.builder().name("당산역").build());
        Station 잠실역 = stationRepository.save(Station.builder().name("잠실역").build());

        newMember.addFavorite(당산역, 잠실역);

        // when
        Member findMember = memberRepository.findByEmail("chh9975");
        Favorite favorite = favoriteRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("존재하는 값이 없습니다"));
        findMember.deleteFavorite(favorite);

        // then
        assertAll(
                () -> assertThat(newMember.getFavorites()).hasSize(0)
        );
    }
}
