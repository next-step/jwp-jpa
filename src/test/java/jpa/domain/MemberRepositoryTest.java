package jpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository members;

    @Autowired
    FavoriteRepository favorites;

    @Test
    void save() {
        Member expected = new Member("jason");
        expected.addFavorite(favorites.save(new Favorite()));
        Member actual = members.save(expected);
        // Member 객체 내에 favorite 목록을 가지고 있지만, DB상의 외래키는 FAVORITE 테이블이 가지게 된다.
        // 이를 통해 다대일 양방향 관계를 추천한다.
        members.flush(); // transaction commit
    }
}
