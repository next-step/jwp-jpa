package jpa.repositories;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.of(30, "hazard@gmail.com", "1234"));
    }

    @Test
    @DisplayName("회원 추가")
    void save() {
        // given, when
        Member actual = memberRepository.save(Member.of(33, "justhis@gmail.com", "1234"));

        // then
        MemberAssertAll(actual, 33, "justhis@gmail.com", "1234");
    }

    @Test
    @DisplayName("회원 수정")
    void update() {
        // given
        String changeEmail = "justhis@gmail.com";

        // when
        Member Member = memberRepository.findByEmail("hazard@gmail.com");
        Member.setEmail(changeEmail);
        Member actual = memberRepository.save(Member);

        // then
        assertThat(actual.getEmail()).isEqualTo("justhis@gmail.com");
    }

    @Test
    @DisplayName("회원 삭제")
    void delete() {
        // given when
        memberRepository.delete(memberRepository.findByEmail("hazard@gmail.com"));
        Member actual = memberRepository.findByEmail("hazard@gmail.com");

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("회원 조회")
    void select() {
        // given when
        Member actual = memberRepository.findByEmail("hazard@gmail.com");

        // then
        MemberAssertAll(actual, 30,"hazard@gmail.com", "1234");
    }

    private void MemberAssertAll(Member actual, int expectedAge, String expectedEmail, String expectedPassword) {
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expectedAge),
                () -> assertThat(actual.getEmail()).isEqualTo(expectedEmail),
                () -> assertThat(actual.getPassword()).isEqualTo(expectedPassword),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("사용자는 여러 즐겨찾기를 가질 수 있다.")
    void memberFavorites() {
        // given
        saves();
        // when
        Member member_justhis = memberRepository.findByEmail("justhis@gmail.com");
        Member member_hazard = memberRepository.findByEmail("hazard@gmail.com");
        // then
        assertThat(member_hazard.getFavorites()).isEmpty();
        assertThat(member_justhis).isNotNull();
        assertThat(member_justhis.getFavorites()).hasSize(3);
        assertThat(member_justhis.getFavorites().get(0).getDepartureStation().getName()).isEqualTo("고속터미널");
        assertThat(member_justhis.getFavorites().get(0).getArrivalStation().getName()).isEqualTo("반포");
        assertThat(member_justhis.getFavorites().get(2).getDepartureStation().getName()).isEqualTo("강남구청");
        assertThat(member_justhis.getFavorites().get(2).getArrivalStation().getName()).isEqualTo("논현");

    }

    private void saves() {
        Member member = memberRepository.save(Member.of(33, "justhis@gmail.com", "1234"));
        member.setFavorite(Favorite.of(Station.of("고속터미널"),Station.of("반포")));
        member.setFavorite(Favorite.of(Station.of("내방"),Station.of("이수")));
        member.setFavorite(Favorite.of(Station.of("강남구청"),Station.of("논현")));
        memberRepository.flush();
        entityManager.clear();
    }
}