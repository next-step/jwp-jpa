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
    void save() {
        Member expected = new Member();
        expected.addFavorite(favoriteRepository.save(new Favorite()));
        Member actual = memberRepository.save(expected);
        memberRepository.flush();
        assertThat(actual).isEqualTo(expected);
    }

}