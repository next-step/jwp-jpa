package jpa.domain.member;

import jpa.domain.favorite.Favorite;
import jpa.domain.favorite.FavoriteRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    void setUp() {
        Member member1 = Member.builder()
                .age(20)
                .email("pobi@test.com")
                .password("12345")
                .build();

        Member member2 = Member.builder()
                .age(30)
                .email("slamdunk@test.com")
                .password("678910")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

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
            .member(member1)
            .build());

        favoriteRepository.save(Favorite.builder()
                .departure(jamsil_station)
                .arrival(pangyo_station)
                .member(member2)
                .build());
    }

    @Test
    @DisplayName("Member 추가 테스트")
    void insert_member_test() {
        // given
        Member member = Member.builder()
                .age(10)
                .email("ykj@test.com")
                .password("757575")
                .build();

        // when
        Member persistMember = memberRepository.save(member);

        // then
        assertThat(persistMember.getId()).isNotNull();
        assertThat(persistMember.getCreatedDate()).isNotNull();
        assertThat(persistMember.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Member 조회 테스트")
    void select_member_test() {
        // given
        Member member = Member.builder()
                .age(10)
                .email("ykj@test.com")
                .password("757575")
                .build();

        // when
        Member persistMember = memberRepository.save(member);

        // then
        assertAll(
                () -> assertThat(persistMember.getAge()).isEqualTo(10),
                () -> assertThat(persistMember.getEmail()).isEqualTo("ykj@test.com"),
                () -> assertThat(persistMember.getPassword()).isEqualTo("757575")
        );
    }

    @Test
    @DisplayName("Member 전체 조회 테스트")
    void select_all_member_test() {
        // given
        Member member = Member.builder()
                .age(10)
                .email("ykj@test.com")
                .password("757575")
                .build();
        memberRepository.save(member);

        // when
        List<Member> members = memberRepository.findAll();
        List<String> memberEmails = members.stream()
                .map(Member::getEmail)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(memberEmails.size()).isEqualTo(3),
                () -> assertThat(memberEmails).contains("pobi@test.com", "slamdunk@test.com", "ykj@test.com")
        );
    }

    @Test
    @DisplayName("Member 수정 테스트")
    void update_member_test() {
        // given
        String changeEmail = "change@test.com";

        // when
        Member member = memberRepository.findByEmail("pobi@test.com");
        member.updateEmail(changeEmail);

        Member updatedMember = memberRepository.findByEmail(changeEmail);

        // then
        assertThat(updatedMember.getEmail()).isEqualTo(changeEmail);
    }

    @Test
    @DisplayName("Member 삭제 테스트")
    void delete_member_test() {
        // given
        Member member = memberRepository.findByEmail("slamdunk@test.com");

        // when
        memberRepository.delete(member);

        // then
        Member deletedMember = memberRepository.findByEmail("slamdunk@test.com");
        assertThat(deletedMember).isNull();
    }

    @Test
    @DisplayName("사용자는 여러 즐겨찾기를 가질 수 있다.")
    void member_have_favorites_test() {
        // given
        Member member = memberRepository.findByEmail("slamdunk@test.com");

        // when
        List<Favorite> favorites = member.getFavorite();

        // then
        assertAll(
                () -> assertThat(favorites).hasSize(1),
                () -> assertThat(favorites).contains(Favorite.builder()
                        .departure(Station.builder()
                                .name("잠실역")
                                .build())
                        .arrival(Station.builder()
                                .name("판교역")
                                .build())
                        .member(member)
                        .build())
        );
    }

}
