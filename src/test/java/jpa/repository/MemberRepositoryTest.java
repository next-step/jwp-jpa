package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Member member = new Member(29, "louis@gmail.com");
        Member result = memberRepository.save(member);
        assertAll(
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getId()).isEqualTo(1),
                () -> assertThat(result.getAge()).isEqualTo(29),
                () -> assertThat(result.getEmail()).isEqualTo("louis@gmail.com")
        );
    }

    @Test
    @DisplayName("나이로 list 조회해보기")
    void findByAge() {
        Member member = new Member(17);
        Member member2 = new Member(17);
        memberRepository.save(member);
        memberRepository.save(member2);
        List<Member> result = memberRepository.findByAge(17);
        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0).getAge()).isEqualTo(17),
                () -> assertThat(result.get(1).getAge()).isEqualTo(17)
        );
    }

    @Test
    @DisplayName("이메일로 찾아서 비교해보기")
    void findByEmail() {
        Member member = new Member("test@naver.com");
        Member result = memberRepository.save(member);
        assertThat(result.getEmail()).isEqualTo("test@naver.com");
    }

    @Test
    @DisplayName("즐겨찾기 추가 테스트")
    void saveFavorite() {
        Member member = new Member("test@naver.com");
        member.addFavorite(favoriteRepository.save(new Favorite(member)));
        Member result = memberRepository.save(member);
        memberRepository.flush();
        assertThat(result.getFavorites().get(0).getId()).isEqualTo(1L);
    }
}
