package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberAndFavoriteRelationTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void saveWithFavorite() {
        Member expected = new Member();
        expected.addFavorite(favoriteRepository.save(new Favorite()));
        expected.addFavorite(favoriteRepository.save(new Favorite()));
        Member actual = memberRepository.save(expected);
        assertThat(actual.getFavorites().size()).isEqualTo(2);
    }

    @Test
    void saveWithMember() {
        Favorite favorite1 = favoriteRepository.save(new Favorite());
        Favorite favorite2 = favoriteRepository.save(new Favorite());
        Member member = memberRepository.save(new Member());
        favorite1.setMember(member);
        favorite2.setMember(member);
        favoriteRepository.flush();
        assertThat(favorite1.getMember().getFavorites().size()).isEqualTo(2);
    }

}