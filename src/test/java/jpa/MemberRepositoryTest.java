package jpa;

import jpa.domain.Favorite;
import jpa.domain.FavoriteRepository;
import jpa.domain.Member;
import jpa.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Member expected = new Member(10, "mj@naver.com");
        Member actual = memberRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @ParameterizedTest()
    @ValueSource(strings = {"mj2@naver.com", "mj3@naver.com"})
    void findByAge(String email) {
        memberRepository.save(new Member(8, "mj1@naver.com"));
        memberRepository.save(new Member(10, "mj2@naver.com"));
        memberRepository.save(new Member(10, "mj3@naver.com"));

        List<Member> members = memberRepository.findByAge(10);
        assertAll(
                () -> assertThat(members.size()).isEqualTo(2),
                () -> assertThat(members.contains(
                        new Member(10, email)))
        );
    }

    @Test
    void findByEmail() {
        memberRepository.save(new Member(8, "mj1@naver.com"));
        memberRepository.save(new Member(10, "mj2@naver.com"));

        Optional<Member> member = memberRepository.findByEmail("mj2@naver.com");
        assertThat(member).isPresent();
        assertThat(member.get().getAge()).isEqualTo(10);
    }

    @Test
    void updateEmail() {
        Member exptectd = memberRepository.save(new Member(8, "mj1@naver.com"));

        exptectd.changeEmail("mj2@naver.com");

        Optional<Member> actual = memberRepository.findByEmail(exptectd.getEmail());
        assertThat(actual).isPresent();
        assertThat(actual.get().getEmail()).isEqualTo(exptectd.getEmail());
    }

    @Test
    void delete() {
        Member exptectd = memberRepository.save(new Member(8, "mj1@naver.com"));

        memberRepository.deleteById(exptectd.getId());
        memberRepository.flush();

        Optional<Member> member = memberRepository.findById(exptectd.getId());
        assertThat(member).isNotPresent();
    }

    @Test
    @DisplayName("사용자 조회시 여러 즐겨찾기 조회")
    void findWithFavorite_test() {
        Member save = memberRepository.save(new Member(8, "mj1@naver.com"));
        Favorite favorite = favoriteRepository.save(new Favorite());
        Favorite favorite2 = favoriteRepository.save(new Favorite());

        save.addFavorite(favorite);
        save.addFavorite(favorite2);

        Member found = memberRepository.findById(save.getId()).get();
        assertThat(found.getFavorites().size()).isEqualTo(2);
    }
}