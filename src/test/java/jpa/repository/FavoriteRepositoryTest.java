package jpa.repository;

import jpa.entity.Favorite;
import jpa.entity.Member;
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

    @Test
    void save() {
        Favorite favorite1 = new Favorite();
        Favorite result = favoriteRepository.save(favorite1);
        assertAll(
                () -> assertThat(result.getId()).isNotNull()
        );
    }

    @Test
    void findById() {
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        Favorite favorite2 = favoriteRepository.findById(favorite1.getId()).get();
        assertThat(favorite1 == favorite2).isTrue();
    }

    @Test
    @DisplayName("즐겨찾기 멤버 연관관계 테스트")
    void saveMember() {
        Member member = new Member(29);
        memberRepository.save(member);
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        favorite1.setMember(member);
        favoriteRepository.flush();
        assertThat(favorite1.getMember().getAge()).isEqualTo(29);
    }
}
