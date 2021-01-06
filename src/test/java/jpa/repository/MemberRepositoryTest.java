package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import jpa.entity.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void create() {
        // given
        Member expected = Member.builder().age(20).email("chh9975").password("1234").build();

        // when
        Member actual = memberRepository.save(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add() {
        // given
        Member member = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        // when
        Station 당산역 = Station.builder().name("당산역").build();
        Station 강남역 = Station.builder().name("강남역").build();

        member.addFavorite(강남역, 당산역);

        Station 잠실역 = Station.builder().name("잠실역").build();
        member.addFavorite(강남역, 잠실역);

        // then
        assertThat(member.getFavorites().size()).isEqualTo(2);
    }

    @Test
    void delete() {
        // given
        Member expected = memberRepository.save(Member.builder()
                            .age(30)
                            .email("hyehwanchoi")
                            .password("1234")
                            .build());

        // when
        memberRepository.delete(expected);

        // then
        Member deletedMember = memberRepository.findByEmail("hyehwanchoi");
        assertThat(deletedMember).isNull();
    }

    @Test
    void update() {
        // given
        Member member = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        // when
        member.changeEmail("nextstep.com");

        Member actual = memberRepository.findByEmail("nextstep.com");

        // then
        assertThat(actual.getEmail()).isEqualTo("nextstep.com");
    }

    @Test
    void deleteFavorite() {
        // given
        Member member = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        // when
        Station 당산역 = Station.builder().name("당산역").build();
        Station 강남역 = Station.builder().name("강남역").build();

        member.addFavorite(강남역, 당산역);

        Station 잠실역 = Station.builder().name("잠실역").build();
        member.addFavorite(강남역, 잠실역);

        assertThat(member.getFavorites().size()).isEqualTo(2);

        member.deleteFavorite(Favorite.builder().sourceStation(강남역).destinationStation(당산역).build());
        assertThat(member.getFavorites().size()).isEqualTo(1);
    }

    @Test
    void read() {
        // given
        Member expected = memberRepository.save(Member.builder().age(20).email("chh9975").password("1234").build());

        // when
        Member actual = memberRepository.findByEmail("chh9975");

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
